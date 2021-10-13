<template>
	<v-container>
		<v-card :loading="loading">
			<v-card-title> {{ title }} </v-card-title>
		</v-card>
		<v-row class="mt-2">
			<v-col v-for="slide in slides" :key="slide.id" cols="6" md="4">
				<v-img :src="src(slide)">
					<template #placeholder>
						<v-row class="fill-height ma-0" align="center" justify="center">
							<v-progress-circular indeterminate color="grey lighten-5" />
						</v-row>
					</template>
				</v-img>
			</v-col>
		</v-row>
	</v-container>
</template>

<script>
export default {
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
		src(slide) {
			return `/api/motd/slides/${slide.id}/image`;
		},
	},
	mounted() {
		this.fetch();
	},
};
</script>