<template>
	<v-main>
		<v-container v-if="!presentation" fill-height fluid>
			<v-row class="fill-height" align="center">
				<v-col align="center">
					<template v-if="loading">
						<h2>loading...</h2>
						<v-progress-circular indeterminate />
					</template>
					<template v-else>
            <v-icon size="64">mdi-bee</v-icon>
						<h4 class="text--secondary">no active presentation</h4>
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
	}),
	methods: {
		async fetch() {
			if (this.loading) {
				return;
			}

			this.loading = true;

			try {
				const presentation = (await this.$http.get("/motd/presentations/active")).data;
				const slides = (await this.$http.get(`/motd/presentations/${presentation.id}/slides`)).data;

				this.presentation = presentation;
				this.slides = slides;
			} catch (error) {
				console.log(error);
			}

			this.loading = false;
		},
	},
	mounted() {
		this.drawerAtOpen = this.$store.state.ui.drawer;

		this.$store.commit("ui/setDrawer", false);
		this.$store.commit("ui/setEnabled", false);

		this.fetch();
	},
	destroyed() {
		this.$store.commit("ui/setDrawer", this.drawerAtOpen);
		this.$store.commit("ui/setEnabled", true);
	},
};
</script>