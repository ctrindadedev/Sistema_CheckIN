import { NavLink } from "react-router-dom";
import styled from "styled-components";

const Nav = styled.nav`
  display: flex;
  align-items: center;
`;

const NavList = styled.ul`
  list-style: none;
  display: flex;
  gap: 1rem;
  font-weight: 500;
`;

const StyledLink = styled(NavLink)`
  color: ${({ theme }) => theme.colors.muted};
  transition: color 150ms ease;
  padding: 0.25rem 0.4rem;
  border-radius: ${({ theme }) => theme.radii.sm};

  &[aria-current="page"],
  &:hover {
    color: ${({ theme }) => theme.colors.primary};
    background: rgba(79, 70, 229, 0.08);
  }
`;

const NavBar = () => {
  return (
    <Nav>
      <NavList>
        <li>
          <StyledLink to="/">Eventos</StyledLink>
        </li>
        <li>
          <StyledLink to="/meus-eventos">Meus Eventos</StyledLink>
        </li>
      </NavList>
    </Nav>
  );
};

export default NavBar;
