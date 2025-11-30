import React from "react";
import { Routes, Route } from "react-router-dom";
import styled from "styled-components";
import Header from "./components/Header";
import Footer from "./components/Footer";
import Home from "./pages/Home";
import Auth from "./pages/Auth";
import Evento from "./pages/Evento";
import EventoUser from "./pages/EventoUser";
import { AuthProvider } from "./context/AuthContext";
import { EventProvider } from "./context/EventContext";

const AppShell = styled.div`
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background: ${({ theme }) => theme.colors.background};
`;

const Main = styled.main`
  width: min(1200px, 100%);
  padding: 2rem 1.5rem 3rem;
  margin: 0 auto;
  flex: 1;
`;

const App: React.FC = () => {
  return (
    <AuthProvider>
      <EventProvider>
        <AppShell>
          <Header />
          <Main>
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/auth" element={<Auth />} />
              <Route path="/evento/:id" element={<Evento />} />
              <Route path="/meus-eventos" element={<EventoUser />} />
            </Routes>
          </Main>
          <Footer />
        </AppShell>
      </EventProvider>
    </AuthProvider>
  );
};

export default App;
