<template>
	<v-dialog v-model="dialog" :persistent="loading" width="500">
		<v-card :loading="loading">
			<v-card-title>{{ $t("references.dialog.import.title") }}</v-card-title>
			<v-divider />
			<v-card-text>
				<v-file-input :disabled="loading" multiple :label="$t('references.dialog.import.fields.files.label')" @change="change" hide-details></v-file-input>
			</v-card-text>
			<v-list v-if="files.length" dense>
				<v-list-item v-for="(entry, index) in files" :key="index" dense>
					<v-list-item-avatar>
						<v-progress-circular v-if="entry.uploading" indeterminate />
						<v-icon v-else-if="entry.success"> mdi-check </v-icon>
						<v-icon v-else> mdi-upload </v-icon>
					</v-list-item-avatar>
					<v-list-item-content>
						<v-list-item-title>{{ entry.file.name }}</v-list-item-title>
						<v-list-item-subtitle color="danger" v-if="true || entry.error">{{ entry.error }}</v-list-item-subtitle>
						<v-progress-linear v-if="entry.uploading" :value="entry.progress" />
					</v-list-item-content>
				</v-list-item>
			</v-list>
			<v-divider />
			<v-card-actions>
				<v-spacer />
				<v-btn :disabled="!canUpload" @click="upload" color="primary">{{ $t("references.dialog.import.actions.upload") }}</v-btn>
				<v-btn :disabled="loading" @click="dialog = false" color="primary">{{ $t("references.dialog.import.actions.cancel") }}</v-btn>
			</v-card-actions>
		</v-card>
	</v-dialog>
</template>

<script>
export default {
	data: () => ({
		dialog: false,
		loading: false,
		files: [],
	}),
	computed: {
		canUpload() {
			return !this.loading && this.files.length;
		},
	},
	methods: {
		open() {
			this.dialog = true;
		},
		close() {
			this.dialog = false;
		},
		change(files) {
			this.files = [];

			for (const file of files) {
				this.files.push({
					file,
					reference: null,
					error: null,
					uploading: false,
					success: false,
					progress: 0,
				});
			}
		},
		upload() {
			if (!this.canUpload) {
				return;
			}

			this.loading = true;

			let active = this.files.length;

			for (const file of this.files) {
				file.uploading = true;
				file.success = false;
				file.progress = 0;

				let data = new FormData();
				data.append("file", file.file);

				this.$http
					.post("references", data, {
						onUploadProgress: function (progressEvent) {
							file.progress = Math.round((progressEvent.loaded * 100) / progressEvent.total);
						},
					})
					.then((response) => {
						file.reference = response.data.payload;
						file.success = true;
						file.error = null;
					})
					.catch((error) => {
						switch (error.response?.status) {
							case 406: {
								file.error = this.$t("references.dialog.import.errors.not-acceptable");
								break;
							}

							case 409: {
								file.error = this.$t("references.dialog.import.errors.conflict");
								break;
							}

							case 500: {
								file.error = `${this.$t("references.dialog.import.errors.internal")}: ${error.response.data.message}`;
								break;
							}

							default: {
								file.error = error;
								break;
							}
						}
					})
					.then(() => {
						file.uploading = false;

						if (--active == 0) {
							this.loading = false;
							this.$emit("uploaded");
						}
					});
			}
		},
	},
};
</script>