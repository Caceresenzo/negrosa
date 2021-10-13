<template>
	<div>
		<v-navigation-drawer app clipped style="left: 256px">
			<v-list dense nav>
				<v-list-item>
					<v-list-item-content>
						<v-list-item-title>Presentations</v-list-item-title>
					</v-list-item-content>
					<v-list-item-action class="my-0" style="flex-direction: row">
						<v-btn icon class="mx-2">
							<v-icon>mdi-plus</v-icon>
						</v-btn>
						<v-btn icon :loading="loading" @click="fetch">
							<v-icon>mdi-refresh</v-icon>
						</v-btn>
					</v-list-item-action>
				</v-list-item>
				<v-divider />
				<v-list-item v-for="presentation in presentations" :key="presentation.id" link :to="to(presentation)">
					<v-list-item-content>
						<v-list-item-title>{{ presentation.name }}</v-list-item-title>
						<v-list-item-subtitle>{{ new Date(presentation.createdAt).toLocaleString() }}</v-list-item-subtitle>
					</v-list-item-content>
				</v-list-item>
			</v-list>
		</v-navigation-drawer>
		<v-main>
			<router-view :key="presentationId" />
		</v-main>
	</div>
</template>

<script>
export default {
	data: () => ({
		loading: false,
		presentations: [],
	}),
  computed: {
    presentationId() {
			return this.$route.params.id;
    }
  },
	methods: {
		async fetch() {
			if (this.loading) {
				return;
			}

			this.loading = true;

			try {
				const presentation = (await this.$http.get("/motd/presentations")).data;
				presentation.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));

				this.presentations = presentation;
			} catch (error) {
				console.log(error);
				alert(`Could not fetch`);
			}

			this.loading = false;
		},
		to(presentation) {
			return `/motd/${presentation.id}`;
		},
	},
	mounted() {
		this.fetch();
	},
};
</script>