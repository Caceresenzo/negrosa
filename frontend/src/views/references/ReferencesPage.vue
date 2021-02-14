<template>
	<v-container>
		<v-card :loading="loading">
			<v-card-title height="150">
				{{ $t("references._") }}
				<v-spacer />
				<v-tooltip top>
					<template v-slot:activator="{ on, attrs }">
						<v-btn :disabled="loading" @click="openImportDialog" icon v-bind="attrs" v-on="on">
							<v-icon>mdi-plus</v-icon>
						</v-btn>
					</template>
					<span>{{ $t("references.actions.add.tooltip") }}</span>
				</v-tooltip>
				<v-tooltip top>
					<template v-slot:activator="{ on, attrs }">
						<v-btn :disabled="loading" @click="refresh" icon v-bind="attrs" v-on="on">
							<v-icon>mdi-refresh</v-icon>
						</v-btn>
					</template>
					<span>{{ $t("references.actions.refresh.tooltip") }}</span>
				</v-tooltip>
			</v-card-title>
		</v-card>
		<v-row class="mt-2">
			<v-col v-for="reference in references" :key="reference" cols="6" md="2">
				<v-hover v-slot="{ hover }">
					<v-card :elevation="hover ? 12 : 2">
						<v-img height="150" :src="`/api/references/${reference}`">
							<v-chip class="ma-1" small style="mix-blend-mode: difference">
								{{ reference }}
							</v-chip>
						</v-img>
						<v-expand-transition>
							<div v-if="hover" class="d-flex transition-fast-in-fast-out primary v-card--reveal display-3 white--text" style="height: 100%">
								<v-tooltip top>
									<template v-slot:activator="{ on, attrs }">
										<v-btn dark :href="`/api/references/${reference}`" target="_blank" icon v-bind="attrs" v-on="on">
											<v-icon>mdi-open-in-new</v-icon>
										</v-btn>
									</template>
									<span>{{ $t("references.actions.open.tooltip") }}</span>
								</v-tooltip>
								<v-tooltip top>
									<template v-slot:activator="{ on, attrs }">
										<v-btn dark icon @click="deleteReference(reference)" v-bind="attrs" v-on="on">
											<v-icon>mdi-delete</v-icon>
										</v-btn>
									</template>
									<span>{{ $t("references.actions.delete.tooltip") }}</span>
								</v-tooltip>
							</div>
						</v-expand-transition>
					</v-card>
				</v-hover>
			</v-col>
		</v-row>

		<import-dialog ref="importDialog" @uploaded="refresh" />
	</v-container>
</template>

<script>
import ImportDialog from "./dialog/ImportDialog.vue";

export default {
	components: { ImportDialog },
	computed: {
		loading() {
			return this.$store.state.references.loading;
		},
		error() {
			return this.$store.state.references.error;
		},
		references() {
			return this.$store.state.references.list;
		},
		importDialog() {
			return this.$refs.importDialog;
		},
	},
	methods: {
		openImportDialog() {
			this.importDialog.open();
		},
		refresh() {
			return this.$store.dispatch("references/fetch");
		},
		deleteReference(name) {
			if (!confirm(`${name} will be deleted?`)) {
				return;
			}

			return this.$http
				.delete(`references/${name}`)
				.catch((error) => {
					alert(`failed to delete ${name}:\n${error.response?.data?.message || error}`);
				})
				.then(this.refresh);
		},
	},
	mounted() {
		this.refresh();
	},
};
</script>
<style >
	.v-card--reveal {
		align-items: center;
		bottom: 0;
		justify-content: center;
		opacity: 0.5;
		position: absolute;
		width: 100%;
	}
</style>