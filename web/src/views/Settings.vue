<template>
	<v-container fluid>
		<v-card class="mx-auto">
			<v-toolbar dense flat>
				<v-toolbar-title>{{ $t("settings.user-interface") }}</v-toolbar-title>
			</v-toolbar>
			<v-divider></v-divider>
			<v-col>
				<v-row class="ml-4 mr-4 pa-1">
					<v-select v-model="language" :items="$i18n.availableLocales" prepend-icon="mdi-translate" single-line></v-select>
				</v-row>
			</v-col>
			<v-col>
				<v-row class="ml-4 mr-4 pa-1">
					<v-select v-model="theme" :items="themes" prepend-icon="mdi-theme-light-dark" single-line></v-select>
				</v-row>
			</v-col>
		</v-card>
	</v-container>
</template>

<script>
	export default {
		name: "Settings",
		data: () => ({
			language: null,
			theme: null,
			themes: null,
			items: ["a", "b", "c"],
		}),
		watch: {
			language: function(newValue) {
				!!newValue;

				if (newValue != this.$i18n.locale) {
					localStorage.setItem('language', newValue);

					location.reload();
				}
			},
			theme: function(newValue) {
				let dark = newValue == this.themes[1]; // [0] = light, [1] = dark

				if ((this.$vuetify.theme.dark = dark)) {
					localStorage.setItem("dark", "yes");
				} else {
					localStorage.removeItem("dark");
				}
			}
		},
		mounted() {
			this.language = localStorage.language || this.$i18n.locale;

			this.themes = [ this.$t('theme.light'), this.$t('theme.dark') ]
			this.theme = this.themes[0 + !!localStorage.getItem('dark')];
		},
	};
</script>
