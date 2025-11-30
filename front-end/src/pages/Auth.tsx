import React from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import { Formik, Form, Field, ErrorMessage, type FormikHelpers } from "formik";
import * as Yup from "yup";
import { useAuth } from "../context/AuthContext";
import type { LoginPayload } from "../types";

type FormStatus = { type: "success" | "error"; message: string };

const Container = styled.section`
  display: flex;
  justify-content: center;
`;

const Card = styled.div`
  width: min(420px, 100%);
  background: ${({ theme }) => theme.colors.surface};
  border-radius: ${({ theme }) => theme.radii.md};
  padding: 2rem;
  box-shadow: 0 20px 50px rgba(15, 23, 42, 0.12);
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

const Title = styled.h1`
  font-size: 1.5rem;
`;

const StyledForm = styled(Form)`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;

const Label = styled.label`
  display: flex;
  flex-direction: column;
  gap: 0.4rem;
  font-weight: 500;
  color: ${({ theme }) => theme.colors.text};
`;

const Input = styled(Field)`
  padding: 0.8rem 1rem;
  border-radius: ${({ theme }) => theme.radii.sm};
  border: 1px solid rgba(15, 23, 42, 0.15);
  font-size: 1rem;
`;

const ErrorText = styled(ErrorMessage)`
  color: #dc2626;
  font-size: 0.85rem;
`;

const SubmitButton = styled.button`
  border: none;
  border-radius: ${({ theme }) => theme.radii.sm};
  padding: 0.85rem 1rem;
  background: ${({ theme }) => theme.colors.primary};
  color: #fff;
  font-weight: 600;
  transition: opacity 150ms ease;

  &:disabled {
    opacity: 0.65;
  }
`;

const StatusMessage = styled.small<{ $status: "success" | "error" }>`
  color: ${({ $status }) => ($status === "success" ? "#16a34a" : "#dc2626")};
`;

const validationSchema = Yup.object({
  email: Yup.string()
    .email("Digite um e-mail válido.")
    .required("O e-mail é obrigatório."),
  senha: Yup.string()
    .min(6, "A senha precisa ter ao menos 6 caracteres.")
    .required("A senha é obrigatória."),
});

const Auth = () => {
  const { login, isLoading } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (
    values: LoginPayload,
    helpers: FormikHelpers<LoginPayload>
  ) => {
    helpers.setStatus(undefined);
    try {
      await login(values);
      helpers.setStatus({ type: "success", message: "Login realizado!" });
      navigate("/meus-eventos");
    } catch (error) {
      const message =
        error instanceof Error
          ? error.message
          : "Não foi possível entrar. Tente novamente.";
      helpers.setStatus({ type: "error", message });
    } finally {
      helpers.setSubmitting(false);
    }
  };

  return (
    <Container>
      <Card>
        <Title>Acesse sua conta</Title>
        <p>
          Utilize o mesmo e-mail utilizado nas inscrições e acompanhe seus
          ingressos.
        </p>
        <Formik
          initialValues={{ email: "", senha: "" }}
          validationSchema={validationSchema}
          onSubmit={handleSubmit}
        >
          {({ isSubmitting, status }) => {
            const typedStatus = status as FormStatus | undefined;
            return (
              <StyledForm noValidate>
                <Label>
                  E-mail
                  <Input
                    name="email"
                    type="email"
                    placeholder="voce@email.com"
                  />
                  <ErrorText name="email" component="span" />
                </Label>

                <Label>
                  Senha
                  <Input name="senha" type="password" placeholder="••••••••" />
                  <ErrorText name="senha" component="span" />
                </Label>

                {typedStatus && (
                  <StatusMessage $status={typedStatus.type}>
                    {typedStatus.message}
                  </StatusMessage>
                )}

                <SubmitButton
                  disabled={isSubmitting || isLoading}
                  type="submit"
                >
                  {isSubmitting || isLoading ? "Entrando..." : "Entrar"}
                </SubmitButton>
              </StyledForm>
            );
          }}
        </Formik>
      </Card>
    </Container>
  );
};

export default Auth;
