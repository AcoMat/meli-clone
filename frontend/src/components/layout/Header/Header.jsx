import logo from '../../../assets/branding/logo.svg'
import searchIcon from '../../../assets/ui/search-icon.svg'
import cart from '../../../assets/cart/cart.svg'
import './Header.css'
import { useUserContext } from '../../../context/UserContext'
import profilePlaceholder from '../../../assets/ui/profile-placeholder.png'
import { Link, useNavigate } from 'react-router-dom'
import { useState } from 'react'

function Header() {
  const actualQuery = new URLSearchParams(window.location.search).get('query');
  const [searchQuery, setSearchQuery] = useState(actualQuery || '');
  const { user } = useUserContext();
  let navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    if (searchQuery) {
      navigate(`/search?query=${searchQuery}`);
    }
    setSearchQuery('');
  }

  return (
    <header className='header'>
      <div className="content-wrapper lh-1">
        <div className="d-flex flex-column flex-md-row justify-content-center align-items-center mb-2 mx-auto" style={{ width: '100%' }}>
          <div style={{ minWidth: 134, marginRight: 0 }} className="d-flex justify-content-center mb-2 mb-md-0 me-md-3">
            <Link to='/'>
              <img id="logo" alt="logo" src={logo} width={134} height={34} />
            </Link>
          </div>
          <div style={{ maxWidth: 600, flex: 1 }} className="d-flex justify-content-center w-100 w-md-auto">
            <form className='header-search w-100 mx-2' onSubmit={handleSubmit}>
              <input value={searchQuery} onChange={(e) => setSearchQuery(e.target.value)} type='text' name='query' className='mt-0 shadow-sm w-100' placeholder='Buscar productos, marcas y más…' />
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
                      <Link to='/profile' className='align-self-end'>
                        <img src={user.image || profilePlaceholder} height={20} width={20} className='rounded-circle me-2 object-fit-cover' style={{ marginBottom: "-3px" }} />
                      </Link>
                      <Link to='/profile'>
                        {user.firstName}
                      </Link>
                    </li>
                    <li className='ms-4'><Link to='/purchases'>Mis compras</Link></li>
                    <li className='ms-4'><Link to='/favorites'>Favoritos</Link></li>
                    <li className='ms-4'><Link to='/cart'><img src={cart} width="20" height="20" /></Link></li>
                  </>
                  :
                  <>
                    <li className='ms-4 text-end'><Link to='/register'>Creá tu cuenta</Link></li>
                    <li className='ms-4 text-end'><Link to='/login'>Ingresá</Link></li>
                    <li className='ms-4 text-end'>
                      <Link to='/login'>
                        <img src={cart} />
                      </Link>
                    </li>
                  </>
              }
            </ul>
          </div>
        </div>
      </div>
    </header>
  );
}

export default Header