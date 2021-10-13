<template>
	<v-main>
		<v-container v-if="!connected" fill-height fluid>
			<v-row class="fill-height" align="center">
				<v-col align="center">
          <h2>connecting...</h2>
					<v-progress-circular indeterminate />
				</v-col>
			</v-row>
		</v-container>
	</v-main>
</template>

<script>
export default {
	data: () => ({
		loading: false,
		presentation: null,
		drawerAtOpen: null,
		eventSource: null,
		active: null,
		activeId: null,
		connected: false,
	}),
	methods: {
		createSource() {
			const source = new EventSource("/api/motd/presentations/events", { withCredentials: true });

			source.addEventListener("notify_presentation_update", (event) => {
				console.log(event.data);

				this.activeId = event.data;
			});

			source.onopen = () => {
				this.connected = true;
			};

			source.onerror = (error) => {
				this.connected = false;
				console.log(error);

				// setTimeout(() => this.createSource(), 1000);
			};

			this.eventSource = source;
		},
		async fetch() {
			const { activeId } = this;

			this.loading = true;

			const id = activeId || "active";
			try {
				const presentation = (await this.$http.get(`/motd/presentations/${id}`)).data;

				if (this.activeId != activeId) {
					return;
				}

				this.presentation = presentation;
			} catch (error) {
				if (this.activeId != activeId) {
					return;
				}

				console.log(error);
			}

			this.loading = false;
		},
	},
	watch: {
		activeId(val) {
			if (val == "-1") {
				this.presentation = null;
			} else {
				this.fetch();
			}
		},
	},
	mounted() {
		this.drawerAtOpen = this.$store.state.ui.drawer;

		this.$store.commit("ui/setDrawer", false);
		this.$store.commit("ui/setEnabled", false);

		this.createSource();
		this.fetch();
	},
	destroyed() {
		this.eventSource.close();

		this.$store.commit("ui/setDrawer", this.drawerAtOpen);
		this.$store.commit("ui/setEnabled", true);
	},
};
</script>