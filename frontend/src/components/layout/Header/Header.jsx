import logo from '../../../assets/branding/logo.svg'
import searchIcon from '../../../assets/ui/search-icon.svg'
import cart from '../../../assets/cart/cart.svg'
import downLine from '../../../assets/arrows/down-line.svg'
import './Header.css'
import loading from '../../../assets/ui/loading.svg'
import { useNavigate } from 'react-router-dom'
import { useUserContext } from '../../../context/AuthContext'
import { useCategories } from '../../../hooks/useCategories'

function Header({ submitfcn }) {
  const { user } = useUserContext();
  const { categories, error } = useCategories();
  let navigate = useNavigate();

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
          <div className="col-md-6 my-md-0 my-2">
            <ul className='header-links px-0 text-break'>
              <li className='me-4'>
                <div className="dropdown">
                  <button className="bg-transparent border-0 p-0 d-flex" data-bs-toggle="dropdown" aria-expanded="false">
                    <a>Categorías</a>
                    <img src={downLine} style={{ opacity: "0.3" }} width={15} height={12} />
                  </button>
                  <ul className="dropdown-menu header-categories">
                    {
                      Array.isArray(categories) ?
                        categories.map((category) => {
                          return (
                            <li key={category.id}>
                              <button
                                onClick={() => { navigate(`categories/${category.id}`) }}
                                className="dropdown-item header-category-item text-capitalize"
                              >
                                {category.name.replace("-", " ")}
                              </button>
                            </li>
                          );
                        })
                        :
                        <li>
                          <div className="text-center" type="button">
                            <img width={25} height={25} src={loading} />
                          </div>
                        </li>
                    }
                  </ul>
                </div>
              </li>
            </ul>
          </div>
          <div className="col d-flex justify-content-end p-0">
            <ul className='header-links px-0'>
              {
                user ?
                  <>
                    <li className='ms-4 d-flex align-items-end'>
                      <a href='/user' className='align-self-end'>
                        <img src={user.image} height={20} width={20} className='rounded-circle me-2 object-fit-cover' />
                      </a>
                      <a href='/user'>
                        {user.name}
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