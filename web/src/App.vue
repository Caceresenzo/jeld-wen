<template>
	<v-app id="app">
		<v-navigation-drawer clipped app overflow>
			<v-list dense>
				<v-text-field flat solo-inverted hide-details prepend-inner-icon="mdi-magnify" label="Search" class="hidden-sm-and-down ma-2"></v-text-field>
				<v-list-item link to="/">
					<v-list-item-avatar>
						<v-icon>mdi-home</v-icon>
					</v-list-item-avatar>
					<v-list-item-content>
						<v-list-item-title v-text="$t('home.drawer')"></v-list-item-title>
					</v-list-item-content>
				</v-list-item>
				<v-list-item link to="/beacon-data">
					<v-list-item-avatar>
						<v-icon>mdi-history</v-icon>
					</v-list-item-avatar>
					<v-list-item-content>
						<v-list-item-title v-text="$t('beacon-data.drawer')"></v-list-item-title>
					</v-list-item-content>
				</v-list-item>
				<v-list-item link to="/settings">
					<v-list-item-avatar>
						<v-icon>mdi-cog</v-icon>
					</v-list-item-avatar>
					<v-list-item-content>
						<v-list-item-title v-text="$t('settings.drawer')"></v-list-item-title>
					</v-list-item-content>
				</v-list-item>
			</v-list>
		</v-navigation-drawer>

		<v-main>
			<transition name="fade" mode="out-in">
				<router-view></router-view>
			</transition>
		</v-main>

		<v-footer app>
			<span class="px-4">&copy; {{ new Date().getFullYear() }}</span>
			<v-spacer></v-spacer>
			<v-card color="green">
				<v-row>
					<span class="px-4">CONNETED</span>
				</v-row>
			</v-card>
		</v-footer>
	</v-app>
</template>

<script>
	export default {
		name: "App",
		created() {
			console.log(this.$i18n.availableLocales);

			this.$vuetify.theme.dark = false;
			if (localStorage.dark) {
				this.$vuetify.theme.dark = true;
			}
		},
	};
</script>

<style>
	.fade-enter-active,
	.fade-leave-active {
		transition: opacity 0.2s;
	}

	.fade-enter,
	.fade-leave-to {
		opacity: 0;
	}
</style>
