import logo from '../../../assets/branding/logo.svg'
import searchIcon from '../../../assets/ui/search-icon.svg'
import cart from '../../../assets/cart/cart.svg'
import './Header.css'
import { useUserContext } from '../../../context/UserContext'
import profilePlaceholder from '../../../assets/ui/profile-placeholder.png'
import { useNavigate } from 'react-router-dom'

function Header() {
  const { user } = useUserContext();
  let navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    const query = e.target.query.value;
    if (query) {
      navigate(`/search?query=${query}`);
    }
  }

  return (
    <header className='header'>
      <div className="content-wrapper lh-1">
        <div className="d-flex justify-content-center align-items-center mb-2 mx-auto" style={{ width: '100%' }}>
          <div style={{ minWidth: 134, marginRight: 24 }} className="d-flex justify-content-center">
            <a href='/'>
              <img src={logo} width={134} height={34} />
            </a>
          </div>
          <div style={{ maxWidth: 600, flex: 1 }} className="d-flex justify-content-center">
            <form className='header-search w-100' onSubmit={handleSubmit}>
              <input name='query' className='mt-0 shadow-sm' placeholder='Buscar productos, marcas y más…' />
              <button className='position-absolute translate-middle' type='submit'><img src={searchIcon} width={25} height={25} /></button>
            </form>
          </div>
        </div>
        <div className="row pb-1 w-100">
          <div className="col d-flex justify-content-end p-0">
            <ul className='header-links px-0'>
              {
                user ?
                  <>
                    <li className='ms-4 d-flex align-items-end'>
                      <a href='/user' className='align-self-end'>
                        <img src={user.image || profilePlaceholder} height={20} width={20} className='rounded-circle me-2 object-fit-cover' style={{ marginBottom: "-3px" }} />
                      </a>
                      <a href='/user'>
                        {user.firstName}
                      </a>
                    </li>
                    <li className='ms-4'><a href='/purchases'>Mis compras</a></li>
                    <li className='ms-4'><a href='/favorites'>Favoritos</a></li>
                    <li className='ms-4'><a href='/cart'><img src={cart} width="20" height="20" /></a></li>
                  </>
                  :
                  <>
                    <li className='ms-4 text-end'><a href='/register'>Creá tu cuenta</a></li>
                    <li className='ms-4 text-end'><a href='/login'>Ingresá</a></li>
                    <li className='ms-4 text-end'>
                      <a href='/login'>
                        <img src={cart} />
                      </a>
                    </li>
                  </>
              }
            </ul>
          </div>
        </div>
      </div>
    </header>
  );
};

export default Header