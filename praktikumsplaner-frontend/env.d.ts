/// <reference types="vite/client" />

declare module "*.vue" {
  import type { DefineComponent } from "vue";
  const component: DefineComponent<{}, {}, any>;
  export default component;
}

interface ImportMetaEnv {
  readonly VITE_AD2IMAGE_URL: string;
  readonly VITE_APPSWITCHER_SERVER_URL: string;
  readonly VITE_VUE_APP_API_URL: string;
  readonly VITE_APP_SECURITY: boolean;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
