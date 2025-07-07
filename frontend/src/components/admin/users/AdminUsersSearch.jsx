import { useState } from 'react';
import avatarPlaceholder from '../../../assets/ui/profile-placeholder.png';
import { useAdminGetUsers } from '../../../hooks/admin/useAdminGetUsers';
import { Link } from 'react-router-dom';

function AdminUsersSearch() {
  const [search, setSearch] = useState('');
  const { usersList, loading, error } = useAdminGetUsers();

  const filteredUsers = usersList.filter(user =>
    user?.id == search.toLowerCase() ||
    user?.email?.toLowerCase().includes(search.toLowerCase()) ||
    user?.firstName?.toLowerCase().includes(search.toLowerCase()) ||
    user?.lastName?.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className='bg-body p-4 rounded shadow-sm my-4'>
      <h3>Usuarios de la pagina</h3>
      <input
        type="text"
        placeholder="Search users by name..."
        value={search}
        onChange={e => setSearch(e.target.value)}
        style={{ marginBottom: '2rem', width: '100%', padding: '0.5rem' }}
      />
      <div className='rounded-2 overflow-y-auto' style={{ maxHeight: '60vh' }}>
        {loading ? (
          <div className="d-flex justify-content-center p-3">
            <div className="spinner-border spinner-border-sm" role="status">
              <span className="visually-hidden">Loading...</span>
            </div>
          </div>
        ) : error ? (
          <p className='text-danger'>{error}</p>
        ) :
          <ul className='list-group list-group-flush'>
            {
              !usersList || filteredUsers.length === 0 ? (
                <li>No se encontraron usuarios</li>
              ) :
                usersList && filteredUsers.map(user => (
                  <li className='list-group-item p-3 bg-light mb-3 shadow-sm' key={user.id}>
                    <span>{user.id}</span>
                    <img className='img-fluid rounded-circle mx-2' src={avatarPlaceholder} alt={`${user.firstName} ${user.lastName}`} style={{ width: '40px', height: '40px' }} />
                    <Link to={`/admin/users/${user.id}`}>{user.firstName} {user.lastName}</Link>
                  </li>
                ))
            }
          </ul>
        }
      </div >
    </div>
  );
}

export default AdminUsersSearch;
