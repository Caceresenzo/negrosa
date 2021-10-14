<template>
	<div style="display: unset">
		<v-tooltip left>
			<template v-slot:activator="{ on, attrs }">
				<v-btn :loading="loading" icon v-bind="attrs" v-on="on" @click="prompt">
					<v-icon>mdi-plus</v-icon>
				</v-btn>
			</template>
			<span>{{ $t('motd.actions.import.tooltip') }}</span>
		</v-tooltip>
		<input ref="file" type="file" style="display: none" @change="onChange" />
	</div>
</template>

<script>
export default {
	data: () => ({
		loading: false,
	}),
	methods: {
		prompt() {
			this.$refs.file.click();
		},
		async onChange(event) {
			const file = (event.target.files || event.dataTransfer.files)?.[0];
			if (!file) {
				return;
			}

			this.$refs.file.value = null;

			if (this.loading) {
				return;
			}

			this.loading = true;

			try {
				const formData = new FormData();
				formData.append("file", file);

				const presentation = (await this.$http.post("/motd/presentations", formData)).data;

				this.$emit("create", presentation);
        
				this.$dialog.notify.success(this.$t("motd.success.import"));
			} catch (error) {
				console.log(error);

				this.$dialog.notify.error(this.$t("motd.error.import", { error }));
			}

			this.loading = false;
		},
	},
};
</script>