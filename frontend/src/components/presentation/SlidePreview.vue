<template>
	<v-hover v-slot="{ hover }">
		<v-card :loading="loading" :elevation="hover ? 12 : 2">
			<v-img :src="src">
				<template #placeholder>
					<v-row class="fill-height ma-0" align="center" justify="center">
						<v-progress-circular indeterminate color="grey lighten-5" />
					</v-row>
				</template>

				<v-chip v-if="slide.duration != null" class="ma-1" small> {{ slide.duration }}s </v-chip>
			</v-img>
			<v-expand-transition>
				<div v-if="hover" class="d-flex transition-fast-in-fast-out primary v-card--reveal display-3 white--text" style="height: 100%">
					<v-tooltip top>
						<template #activator="{ on, attrs }">
							<v-btn dark :href="src" target="_blank" icon v-bind="attrs" v-on="on">
								<v-icon>mdi-open-in-new</v-icon>
							</v-btn>
						</template>
						<span>{{ $t("motd.actions.open.tooltip") }}</span>
					</v-tooltip>
					<v-tooltip top>
						<template #activator="{ on, attrs }">
							<v-btn dark icon @click="promptSeconds" v-bind="attrs" v-on="on">
								<v-icon>mdi-pencil</v-icon>
							</v-btn>
						</template>
						<span>{{ $t("motd.actions.delete.tooltip") }}</span>
					</v-tooltip>
				</div>
			</v-expand-transition>
		</v-card>
	</v-hover>
</template>

<script>
export default {
	props: {
		presentation: {
			required: true,
			type: Object,
		},
		slide: {
			required: true,
			type: Object,
		},
	},
	data: () => ({
		loading: false,
	}),
	computed: {
		src() {
			return `/api/motd/slides/${this.slide.id}/image`;
		},
	},
	methods: {
		async promptSeconds() {
			const {
				slide: { duration },
				presentation: { slideDuration },
			} = this;

			const value = await this.$dialog.prompt({
				title: this.$t("motd.actions.update-slide-duration.title"),
				text: this.$t("motd.actions.update-slide-duration.text"),
				value: `${duration || slideDuration}`,
				textField: {
					min: 0,
					step: 0.5,
					type: "number",
				},
			});

			if (value != null) {
				if (this.loading) {
					return;
				}

				this.loading = true;

				try {
					const {
						slide: { id },
					} = this;

					const slide = (
						await this.$http.patch(`/motd/slides/${id}`, {
							duration: value,
						})
					).data;

					this.$emit("update", slide);
				} catch (error) {
					console.log(error);

					this.$dialog.notify.error(this.$t("motd.error.update", { error }));
				}

				this.loading = false;
			}
		},
	},
};
</script>

<style>
  .v-card--reveal {
  	align-items: center;
  	bottom: 0;
  	justify-content: center;
  	opacity: 0.5;
  	position: absolute;
  	width: 100%;
  }
</style>
