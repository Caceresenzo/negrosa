<template>
	<v-container>
		<v-card :loading="loading">
			<v-card-title>
				<v-hover close-delay="100" v-slot="{ hover }">
					<div>
						{{ title }}
						<v-btn
							icon
							:style="{
								visibility: hover ? 'visible' : 'hidden',
							}"
							@click="promptNameUpdate"
						>
							<v-icon>mdi-pencil</v-icon>
						</v-btn>
					</div>
				</v-hover>
				<v-spacer />
				<template v-if="presentation">
					<v-btn icon :disabled="loading" @click="toggleActive" class="mx-1">
						<v-icon v-if="presentation.active">mdi-power</v-icon>
						<v-icon v-else>mdi-cloud-upload</v-icon>
					</v-btn>
					<v-btn icon :disabled="loading" @click="promptDelete" class="mx-1">
						<v-icon>mdi-delete</v-icon>
					</v-btn>
				</template>
				<v-btn icon :loading="loading" @click="fetch">
					<v-icon>mdi-refresh</v-icon>
				</v-btn>
			</v-card-title>
			<template v-if="presentation">
				<v-card-subtitle>{{ new Date(presentation.createdAt).toLocaleString() }}</v-card-subtitle>
				<v-card-text>
					<v-hover close-delay="100" v-slot="{ hover }">
						<div>
							{{ $t("motd.text.default-slide-duration", { value: presentation.slideDuration }) }}
							<v-btn
								small
								icon
								:style="{
									visibility: hover ? 'visible' : 'hidden',
								}"
								@click="promptDurationUpdate"
							>
								<v-icon>mdi-pencil</v-icon>
							</v-btn>
						</div>
					</v-hover>
				</v-card-text>
			</template>
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

				this.$dialog.notify.error(this.$t("motd.error.fetch", { error }));
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

				this.$dialog.notify.success(this.$t("motd.success." + (this.presentation.active ? "activate" : "deactivate")));
			} catch (error) {
				console.log(error);

				this.$dialog.notify.error(this.$t("motd.error.update", { error }));
			}

			this.loading = false;
		},
		async promptNameUpdate() {
			const { presentation } = this;

			const response = await this.$dialog.prompt({
				title: this.$t("motd.actions.update-name.title"),
				text: this.$t("motd.actions.update-name.text"),
				value: presentation.name,
			});

			if (response === null || response === false) {
				return;
			}

			if (this.loading) {
				return;
			}

			this.loading = true;

			try {
				this.presentation = (
					await this.$http.patch(`/motd/presentations/${presentation.id}`, {
						name: response,
					})
				).data;

				this.$emit("hydrate", this.presentation);

				this.$dialog.notify.success(this.$t("motd.success.update"));
			} catch (error) {
				console.log(error);

				this.$dialog.notify.error(this.$t("motd.error.update", { error }));
			}

			this.loading = false;
		},
		async promptDurationUpdate() {
			const { presentation } = this;

			const response = await this.$dialog.prompt({
				title: this.$t("motd.actions.update-duration.title"),
				text: this.$t("motd.actions.update-duration.text"),
				value: `${presentation.slideDuration}`,
				textField: {
					min: 1,
					step: 0.5,
					type: "number",
				},
			});

			if (response === null || response === false) {
				return;
			}

			if (this.loading) {
				return;
			}

			this.loading = true;

			try {
				this.presentation = (
					await this.$http.patch(`/motd/presentations/${presentation.id}`, {
						slideDuration: response,
					})
				).data;

				this.$dialog.notify.success(this.$t("motd.success.update"));
			} catch (error) {
				console.log(error);

				this.$dialog.notify.error(this.$t("motd.error.update", { error }));
			}

			this.loading = false;
		},
		async promptDelete() {
			const response = await this.$dialog.confirm({
				title: this.$t("motd.actions.delete.title"),
				text: this.$t("motd.actions.delete.text"),
			});

			if (!response) {
				return;
			}

			if (this.loading) {
				return;
			}

			this.loading = true;

			try {
				const { presentation } = this;

				await this.$http.delete(`/motd/presentations/${presentation.id}`);

				this.$emit("delete", presentation);
				this.$router.push({
					path: "/motd/",
				});

				this.$dialog.notify.success(this.$t("motd.success.delete"));
			} catch (error) {
				console.log(error);

				this.$dialog.notify.error(this.$t("motd.error.delete", { error }));
			}

			this.loading = false;
		},
	},
	mounted() {
		this.fetch();
	},
};
</script>