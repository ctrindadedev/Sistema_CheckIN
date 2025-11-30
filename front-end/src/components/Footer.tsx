import React from "react";
import styled from "styled-components";

const FooterBar = styled.footer`
  width: 100%;
  background: ${({ theme }) => theme.colors.surface};
  border-top: 1px solid rgba(15, 23, 42, 0.08);
`;

const FooterContent = styled.div`
  width: min(1200px, 100%);
  margin: 0 auto;
  padding: 1.5rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 0.9rem;
  color: ${({ theme }) => theme.colors.muted};

  @media (max-width: 640px) {
    flex-direction: column;
    gap: 0.5rem;
    text-align: center;
  }
`;

const Footer = () => {
  return (
    <FooterBar>
      <FooterContent>
        <span>© {new Date().getFullYear()} CheckIN System</span>
        <span>Organize, compartilhe e confirme presenças com facilidade.</span>
      </FooterContent>
    </FooterBar>
  );
};

export default Footer;
