<template>
	<v-main>
		<v-container v-if="!presentation" fill-height fluid>
			<v-row class="fill-height" align="center">
				<v-col align="center">
					<template v-if="loading">
						<v-progress-circular indeterminate />
					</template>
					<template v-else>
						<v-icon size="64">mdi-bee</v-icon>
						<h4 class="text--secondary">{{ $t("motd.text.no-active") }}</h4>
					</template>
				</v-col>
			</v-row>
		</v-container>
		<presentation-slide-show v-else :presentation="presentation" :slides="slides" />
	</v-main>
</template>

<script>
import PresentationSlideShow from "../../components/presentation/SlideShow.vue";

export default {
	components: {
		PresentationSlideShow,
	},
	data: () => ({
		drawerAtOpen: null,
		loading: false,
		presentation: null,
		slides: [],
		interval: null,
	}),
	methods: {
		async fetch() {
			if (this.loading) {
				return;
			}

			this.loading = true;

			try {
				const presentation = (await this.$http.get("/motd/presentations/@active")).data;

				if (this.presentation?.id != presentation.id || this.presentation?.updatedAt != presentation.updatedAt) {
					const slides = (await this.$http.get(`/motd/presentations/${presentation.id}/slides`)).data;

					this.presentation = presentation;
					this.slides = slides;
				}
			} catch (error) {
				if (error?.response?.status == 404) {
					this.presentation = null;
					this.slides = [];
				} else {
					console.log(error);
				}
			}

			this.loading = false;
		},
		schedule() {
			this.cancel();

			this.interval = setInterval(() => this.fetch(), 10_000);
		},
		cancel() {
			if (this.interval != null) {
				clearInterval(this.interval);
			}

			this.interval = null;
		},
	},
	mounted() {
		this.drawerAtOpen = this.$store.state.ui.drawer;

		this.$store.commit("ui/setDrawer", false);
		this.$store.commit("ui/setEnabled", false);

		this.fetch();
		this.schedule();
	},
	destroyed() {
		this.$store.commit("ui/setDrawer", this.drawerAtOpen);
		this.$store.commit("ui/setEnabled", true);

		this.cancel();
	},
};
</script>