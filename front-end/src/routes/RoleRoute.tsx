import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

interface RoleRouteProps {
  children: React.ReactNode;
  role: string;
}

const RoleRoute = ({ children, role }: RoleRouteProps) => {
  const { user } = useAuth();

  if (!user?.roles?.includes(role)) {
    return <Navigate to="/" replace />;
  }

  return <>{children}</>;
};

export default RoleRoute;
