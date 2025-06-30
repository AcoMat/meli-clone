import { useEffect, useState } from "react";
import { adminGetUsers } from "../../services/ApiService";
import { getToken } from "../../services/TokenService";

export const useAdminGetUsers = () => {
  const [usersList, setUsersList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchUsers = async () => {
    try {
        const token = await getToken();
        if (!token) {
            throw new Error("No authentication token found");
        }
        const data = await adminGetUsers(token);
        setUsersList(data);
    } catch (err) {
        setError(err.message);
    } finally {
        setLoading(false);
    }
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  return { usersList, loading, error };
}