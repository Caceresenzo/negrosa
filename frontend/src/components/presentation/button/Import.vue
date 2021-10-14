<template>
	<v-btn :loading="loading" icon @click="prompt">
		<v-icon>mdi-plus</v-icon>
		<input ref="file" type="file" style="display: none" @change="onChange" />
	</v-btn>
</template>

<script>
export default {
	data: () => ({
		loading: false,
		file: null,
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
			} catch (error) {
				console.log(error);
				alert("Could not create");
			}

			this.loading = false;
			console.log(file);
		},
	},
	watch: {
		file(val) {
			console.log(val);
		},
	},
};
</script>