import React, { useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import styled from "styled-components";
import { Formik, Form, Field, ErrorMessage, type FormikHelpers } from "formik";
import * as Yup from "yup";
import { useAuth } from "../../context/AuthContext";

type FormStatus = { type: "success" | "error"; message: string };

interface RegisterValues {
  name: string;
  email: string;
  password: string;
  confirmPassword: string;
}

const loginSchema = Yup.object({
  email: Yup.string()
    .email("Digite um e-mail válido.")
    .required("O e-mail é obrigatório."),
  password: Yup.string()
    .min(6, "A senha precisa ter ao menos 6 caracteres.")
    .required("A senha é obrigatória."),
});

const registerSchema = Yup.object({
  name: Yup.string()
    .min(3, "O nome precisa ter ao menos 3 caracteres.")
    .required("O nome é obrigatório."),
  email: Yup.string()
    .email("Digite um e-mail válido.")
    .required("O e-mail é obrigatório."),
  password: Yup.string()
    .min(6, "A senha precisa ter ao menos 6 caracteres.")
    .required("A senha é obrigatória."),
  confirmPassword: Yup.string()
    .oneOf([Yup.ref("password")], "As senhas não coincidem.")
    .required("Confirme sua senha."),
});

const Container = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: calc(100vh - 200px);
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
  cursor: pointer;
  &:disabled {
    opacity: 0.65;
    cursor: not-allowed;
  }
`;

const StatusMessage = styled.small<{ $status: "success" | "error" }>`
  color: ${({ $status }) => ($status === "success" ? "#16a34a" : "#dc2626")};
  font-weight: 500;
`;

const ToggleText = styled.p`
  text-align: center;
  color: ${({ theme }) => theme.colors.muted};
  font-size: 0.9rem;
`;

const ToggleButton = styled.button`
  background: none;
  border: none;
  color: ${({ theme }) => theme.colors.primary};
  font-weight: 600;
  cursor: pointer;
  padding: 0;
  text-decoration: underline;
`;

const Auth = () => {
  const { login, register: registerUser } = useAuth();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const [mode, setMode] = useState<"login" | "register">("login");

  const returnUrl = searchParams.get("returnUrl") || "/";

  const handleLogin = async (
    values: { email: string; password: string },
    helpers: FormikHelpers<{ email: string; password: string }>,
  ) => {
    helpers.setStatus(undefined);
    try {
      await login(values);
      helpers.setStatus({
        type: "success",
        message: "Login realizado com sucesso!",
      });
      setTimeout(() => navigate(returnUrl), 500);
    } catch (error: any) {
      const message =
        error.response?.data?.message ||
        "Credenciais inválidas. Verifique seu e-mail e senha.";
      helpers.setStatus({ type: "error", message });
    } finally {
      helpers.setSubmitting(false);
    }
  };

  const handleRegister = async (
    values: RegisterValues,
    helpers: FormikHelpers<RegisterValues>,
  ) => {
    helpers.setStatus(undefined);
    try {
      await registerUser({
        name: values.name,
        email: values.email,
        password: values.password,
      });
      helpers.setStatus({
        type: "success",
        message: "Conta criada com sucesso! Faça login.",
      });
      setTimeout(() => setMode("login"), 1500);
    } catch (error: any) {
      const message =
        error.response?.data?.message ||
        "Não foi possível criar a conta. Tente novamente.";
      helpers.setStatus({ type: "error", message });
    } finally {
      helpers.setSubmitting(false);
    }
  };

  return (
    <Container>
      <Card>
        {mode === "login" && (
          <>
            <Title>Acesse sua conta</Title>
            <p>
              Utilize o e-mail cadastrado nas inscrições para acompanhar seus
              ingressos.
            </p>
            <Formik
              initialValues={{ email: "", password: "" }}
              validationSchema={loginSchema}
              onSubmit={handleLogin}
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
                      <Input
                        name="password"
                        type="password"
                        placeholder="••••••••"
                      />
                      <ErrorText name="password" component="span" />
                    </Label>

                    {typedStatus && (
                      <StatusMessage $status={typedStatus.type}>
                        {typedStatus.message}
                      </StatusMessage>
                    )}

                    <SubmitButton disabled={isSubmitting} type="submit">
                      {isSubmitting ? "Entrando..." : "Entrar"}
                    </SubmitButton>
                  </StyledForm>
                );
              }}
            </Formik>

            <ToggleText>
              Primeira vez aqui?{" "}
              <ToggleButton onClick={() => setMode("register")}>
                Criar conta
              </ToggleButton>
            </ToggleText>
          </>
        )}

        {mode === "register" && (
          <>
            <Title>Crie sua conta</Title>
            <p>Preencha os dados abaixo para se registrar na plataforma.</p>
            <Formik
              initialValues={{
                name: "",
                email: "",
                password: "",
                confirmPassword: "",
              }}
              validationSchema={registerSchema}
              onSubmit={handleRegister}
            >
              {({ isSubmitting, status }) => {
                const typedStatus = status as FormStatus | undefined;
                return (
                  <StyledForm noValidate>
                    <Label>
                      Nome
                      <Input
                        name="name"
                        type="text"
                        placeholder="Seu nome completo"
                      />
                      <ErrorText name="name" component="span" />
                    </Label>

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
                      <Input
                        name="password"
                        type="password"
                        placeholder="••••••••"
                      />
                      <ErrorText name="password" component="span" />
                    </Label>

                    <Label>
                      Confirmar senha
                      <Input
                        name="confirmPassword"
                        type="password"
                        placeholder="••••••••"
                      />
                      <ErrorText name="confirmPassword" component="span" />
                    </Label>

                    {typedStatus && (
                      <StatusMessage $status={typedStatus.type}>
                        {typedStatus.message}
                      </StatusMessage>
                    )}

                    <SubmitButton disabled={isSubmitting} type="submit">
                      {isSubmitting ? "Criando conta..." : "Criar conta"}
                    </SubmitButton>
                  </StyledForm>
                );
              }}
            </Formik>

            <ToggleText>
              Já tem conta?{" "}
              <ToggleButton onClick={() => setMode("login")}>
                Fazer login
              </ToggleButton>
            </ToggleText>
          </>
        )}
      </Card>
    </Container>
  );
};

export default Auth;
