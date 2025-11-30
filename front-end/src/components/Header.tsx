import React from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import NavBar from "./NavBar";
import { useAuth } from "../context/AuthContext";

const HeaderBar = styled.header`
  width: 100%;
  background: ${({ theme }) => theme.colors.surface};
  border-bottom: 1px solid rgba(15, 23, 42, 0.08);
  position: sticky;
  top: 0;
  z-index: 10;
`;

const HeaderContent = styled.div`
  width: min(1200px, 100%);
  margin: 0 auto;
  padding: 1.2rem 1.5rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 2rem;
`;

const Brand = styled(Link)`
  font-size: 1.4rem;
  font-weight: 700;
  letter-spacing: -0.02em;
  color: ${({ theme }) => theme.colors.primary};
`;

const Actions = styled.div`
  display: flex;
  align-items: center;
  gap: 1rem;
`;

const PrimaryLink = styled(Link)`
  padding: 0.65rem 1.2rem;
  border-radius: ${({ theme }) => theme.radii.sm};
  background: ${({ theme }) => theme.colors.primary};
  color: #fff;
  font-weight: 600;
  transition: filter 150ms ease;

  &:hover {
    filter: brightness(0.92);
  }
`;

const UserBadge = styled.div`
  display: flex;
  align-items: center;
  gap: 0.65rem;
  padding: 0.55rem 1rem;
  border-radius: 999px;
  background: rgba(79, 70, 229, 0.08);
  color: ${({ theme }) => theme.colors.primary};
  font-weight: 600;
`;

const LogoutButton = styled.button`
  border: none;
  background: transparent;
  color: ${({ theme }) => theme.colors.muted};
  font-size: 0.9rem;
  padding: 0.2rem 0.4rem;
  transition: color 150ms ease;

  &:hover {
    color: ${({ theme }) => theme.colors.primary};
  }

  &:disabled {
    opacity: 0.5;
    cursor: not-allowed;
  }
`;

const Header: React.FC = () => {
  const { isAuthenticated, user, logout, isLoading } = useAuth();

  const handleLogout = async () => {
    await logout();
  };

  return (
    <HeaderBar>
      <HeaderContent>
        <Brand to="/">CheckIN</Brand>
        <Actions>
          <NavBar />
          {isAuthenticated ? (
            <UserBadge>
              <span>{user?.nome ?? "Participante"}</span>
              <LogoutButton onClick={handleLogout} disabled={isLoading}>
                sair
              </LogoutButton>
            </UserBadge>
          ) : (
            <PrimaryLink to="/auth">Entrar</PrimaryLink>
          )}
        </Actions>
      </HeaderContent>
    </HeaderBar>
  );
};

export default Header;
