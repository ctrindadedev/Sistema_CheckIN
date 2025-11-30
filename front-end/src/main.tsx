import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import { ThemeProvider } from "styled-components";
import App from "./App.tsx";
import { GlobalStyle } from "./styles/global.ts";

const theme = {
  colors: {
    primary: "#4f46e5",
    secondary: "#14b8a6",
    text: "#0b0d0f",
    muted: "#6b7280",
    background: "#f5f6fb",
    surface: "#ffffff",
  },
  radii: {
    sm: "8px",
    md: "16px",
  },
};

createRoot(document.getElementById("root")!).render(
  <BrowserRouter>
    <StrictMode>
      <ThemeProvider theme={theme}>
        <GlobalStyle />
        <App />
      </ThemeProvider>
    </StrictMode>
  </BrowserRouter>
);
