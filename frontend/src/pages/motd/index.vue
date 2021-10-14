<template>
	<div>
		<v-navigation-drawer v-model="drawer" disable-resize-watcher app clipped style="left: 256px">
			<template #prepend>
				<v-list-item>
					<v-spacer />
					<v-list-item-action style="flex-direction: row">
						<presentation-button-import class="mx-1" @create="onCreate" />
						<v-btn icon :loading="loading" @click="fetch">
							<v-icon>mdi-refresh</v-icon>
						</v-btn>
					</v-list-item-action>
				</v-list-item>
				<v-divider />
			</template>

			<v-list dense nav>
				<v-list-item v-for="presentation in presentations" :key="presentation.id" link :to="to(presentation)">
					<v-list-item-content>
						<v-list-item-title>{{ presentation.name }}</v-list-item-title>
						<v-list-item-subtitle style="height: 18px">
							{{ new Date(presentation.createdAt).toLocaleString() }}
							<v-chip v-if="presentation.active" x-small color="success">active</v-chip>
						</v-list-item-subtitle>
					</v-list-item-content>
				</v-list-item>
			</v-list>
		</v-navigation-drawer>
		<v-main>
			<router-view :key="presentationId" @hydrate="fetch" @delete="fetch" />
		</v-main>
	</div>
</template>

<script>
import PresentationButtonImport from "../../components/presentation/button/Import.vue";

export default {
	components: {
		PresentationButtonImport,
	},
	data: () => ({
		loading: false,
		presentations: [],
	}),
	computed: {
		presentationId() {
			return this.$route.params.id;
		},
		drawer: {
			get() {
				return this.$store.state.ui.drawer;
			},
			set(val) {
				val;
			},
		},
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
		onCreate(presentation) {
			this.presentations.unshift(presentation);

			this.$router.push({
				path: `/motd/${presentation.id}`,
			});
		},
	},
	mounted() {
		this.fetch();
	},
};
</script>