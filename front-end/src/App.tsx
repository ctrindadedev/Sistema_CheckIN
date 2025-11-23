import React from "react";
import { Routes, Route } from "react-router-dom";
import Header from "./components/Header";
import Home from "./pages/Home";
import Auth from "./pages/Auth";
import Evento from "./pages/Evento";
import EventoUser from "./pages/EventoUser";

const App: React.FC = () => {
  return (
    <>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="auth" element={<Auth />} />
        <Route path="/evento/:id" element={<Evento />} />
      </Routes>
    </>
  );
};

export default App;
