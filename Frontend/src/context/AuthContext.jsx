import {
  createContext,
  useContext,
  useEffect,
  useState
} from "react";

const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [token, setToken] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const storedToken = localStorage.getItem("accessToken");
    const storedUser = localStorage.getItem("user");

    if (storedToken) {
      setToken(storedToken);
    }

    if (storedUser) {
      try {
        setUser(JSON.parse(storedUser));
      } catch (error) {
        console.error("Invalid stored user:", error);
        localStorage.removeItem("user");
      }
    }

    setLoading(false);
  }, []);

  const login = (authData) => {
    const {
      token: newToken,
      user: newUser
    } = authData;

    setToken(newToken);
    setUser(newUser);

    localStorage.setItem("accessToken", newToken);
    localStorage.setItem(
      "user",
      JSON.stringify(newUser)
    );
  };

  const logout = () => {
    setUser(null);
    setToken(null);

    localStorage.removeItem("accessToken");
    localStorage.removeItem("user");
  };

  const isAuthenticated = Boolean(token);

  // const isAdmin =
  //   user?.role === "ADMIN" ||
  //   user?.role === "ROLE_ADMIN";

  const value = {
    user,
    token,
    loading,
    isAuthenticated,
    login,
    logout
  };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error(
      "useAuth must be used inside AuthProvider"
    );
  }

  return context;
}