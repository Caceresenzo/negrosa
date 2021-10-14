<template>
	<v-container>
		<v-card :loading="loading">
			<v-card-title>
				{{ title }}
				<v-spacer />
				<v-btn v-if="presentation" icon :disabled="loading" @click="toggleActive" class="mx-2">
					<v-icon v-if="presentation.active">mdi-power</v-icon>
					<v-icon v-else>mdi-cloud-upload</v-icon>
				</v-btn>
				<v-btn icon :loading="loading" @click="fetch">
					<v-icon>mdi-refresh</v-icon>
				</v-btn>
			</v-card-title>
		</v-card>
		<v-row class="mt-2">
			<v-col v-for="(slide, index) in slides" :key="slide.id" cols="6" md="4">
        <presentation-slide-preview :presentation="presentation" :slide="slide" @update="(val) => $set(slides, index, val)" />
			</v-col>
		</v-row>
	</v-container>
</template>

<script>
import PresentationSlidePreview from "../../../components/presentation/SlidePreview.vue";

export default {
	components: {
		PresentationSlidePreview,
	},
	data: () => ({
		loading: false,
		presentation: null,
		slides: [],
	}),
	computed: {
		id() {
			return this.$route.params.id;
		},
		title() {
			return this.presentation?.name || this.id;
		},
	},
	methods: {
		async fetch() {
			if (this.loading) {
				return;
			}

			this.loading = true;

			try {
				const { id } = this;

				const presentation = (await this.$http.get(`/motd/presentations/${id}`)).data;
				const slides = (await this.$http.get(`/motd/presentations/${id}/slides`)).data;
				slides.sort((a, b) => a.position - b.position);

				this.presentation = presentation;
				this.slides = slides;
			} catch (error) {
				console.log(error);
				alert(`Could not fetch`);
			}

			this.loading = false;
		},
		async toggleActive() {
			if (this.loading) {
				return;
			}

			this.loading = true;

			try {
				const { id } = this;

				this.presentation = (
					await this.$http.patch(`/motd/presentations/${id}`, {
						active: !this.presentation.active,
					})
				).data;

				this.$emit("hydrate", this.presentation);
			} catch (error) {
				console.log(error);
				alert(`Could not update`);
			}

			this.loading = false;
		},
	},
	mounted() {
		this.fetch();
	},
};
</script>