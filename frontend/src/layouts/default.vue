<template>
	<v-app id="app">
		<v-app-bar app clipped-left>
			<v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>
			<v-toolbar-title>Negro SA</v-toolbar-title>
		</v-app-bar>
		<v-navigation-drawer v-model="drawer" app clipped>
			<v-list dense nav>
				<v-list-item v-for="item in items" :key="item.key" link :to="item.to">
					<v-list-item-icon>
						<v-icon>{{ item.icon }}</v-icon>
					</v-list-item-icon>
					<v-list-item-content>
						<v-list-item-title>{{ $t(`drawer.${item.key}`) }}</v-list-item-title>
					</v-list-item-content>
				</v-list-item>
			</v-list>
		</v-navigation-drawer>
		<router-view />
	</v-app>
</template>
<script>
export default {
	data: () => ({
		drawer: true,
		items: [
			{
				key: "home",
				to: "/",
				icon: "mdi-home",
			},
			{
				key: "references",
				to: "/references",
				icon: "mdi-ruler",
			},
			{
				key: "motd",
				to: "/motd",
				icon: "mdi-monitor-share",
			},
			{
				key: "settings",
				to: "/settings",
				icon: "mdi-cog",
			},
			{
				key: "about",
				to: "/about",
				icon: "mdi-information-variant",
			},
		],
	}),
	created() {
		this.$vuetify.application.register = function (uid, location, size) {
			this.application[location][uid] = size;
			this.update(location);
		};
	},
};
</script>