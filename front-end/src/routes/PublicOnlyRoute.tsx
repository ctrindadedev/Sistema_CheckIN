import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

interface PublicOnlyRouteProps {
  children: React.ReactNode;
}

const PublicOnlyRoute = ({ children }: PublicOnlyRouteProps) => {
  const { isAuthenticated } = useAuth();

  if (isAuthenticated) {
    return <Navigate to="/" replace />;
  }

  return <>{children}</>;
};

export default PublicOnlyRoute;
