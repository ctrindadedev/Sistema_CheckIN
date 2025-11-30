import "styled-components";

declare module "styled-components" {
  export interface DefaultTheme {
    colors: {
      primary: string;
      secondary: string;
      text: string;
      muted: string;
      background: string;
      surface: string;
    };
    radii: {
      sm: string;
      md: string;
    };
  }
}
