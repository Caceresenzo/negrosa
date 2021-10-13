<template>
	<v-main>
		<v-container>
			<v-card>
				<v-card-title>{{ $t("settings._") }}</v-card-title>
			</v-card>
			<v-card class="mt-4">
				<v-list two-line subheader>
					<v-subheader>{{ $t("settings.general._") }}</v-subheader>

					<v-list-item>
						<v-list-item-content>
							<v-list-item-title>{{ $t("settings.general.language._") }}</v-list-item-title>
							<v-list-item-subtitle>{{ $t("settings.general.language.description") }}</v-list-item-subtitle>
						</v-list-item-content>
						<v-list-item-action>
							<v-select v-model="locale" :items="locales" />
						</v-list-item-action>
					</v-list-item>

					<v-list-item>
						<v-list-item-content>
							<v-list-item-title>{{ $t("settings.general.dark._") }}</v-list-item-title>
							<v-list-item-subtitle>{{ $t("settings.general.dark.description") }}</v-list-item-subtitle>
						</v-list-item-content>
						<v-list-item-action>
							<v-switch v-model="dark" />
						</v-list-item-action>
					</v-list-item>
				</v-list>
			</v-card>
		</v-container>
	</v-main>
</template>

<script>
export default {
	computed: {
		locale: {
			get() {
				return this.$i18n.locale;
			},
			set(val) {
				return (this.$i18n.locale = val);
			},
		},
		locales() {
			return Object.keys(this.$i18n.messages).map((locale) => ({
				text: this.$i18n.messages[locale]._,
				value: locale,
			}));
		},
		dark: {
			get() {
				return this.$vuetify.theme.dark;
			},
			set(val) {
				return (this.$vuetify.theme.dark = val);
			},
		},
	},
	watch: {
		dark(val) {
			localStorage.setItem("dark", val);
		},
	},
};
</script>