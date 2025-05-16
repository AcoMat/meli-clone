import logo from '../../../assets/branding/logo.svg'
import searchIcon from '../../../assets/ui/search-icon.svg'
import cart from '../../../assets/cart/cart.svg'
import './Header.css'
import { useUserContext } from '../../../context/UserContext'
import profilePlaceholder from '../../../assets/ui/profile-placeholder.png'

function Header({ submitfcn }) {
  const { user } = useUserContext();

  return (
    <header className='header'>
      <div className="content-wrapper lh-1">
        <div className="row mb-2 w-100">
          <div className="col-md-2">
            <a href='/'>
              <img src={logo} width={134} height={34} />
            </a>
          </div>
          <div className="col-md-8">
            <form className='header-search' onSubmit={submitfcn}>
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