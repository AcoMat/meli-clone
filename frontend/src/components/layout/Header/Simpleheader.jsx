import logo from '../../../assets/branding/logo.svg'
import './Header.css'

function SimpleHeader() {
    return (
        <header className='header'>
            <div className="content-wrapper lh-1">
                <div className="row mb-2">
                    <div className="col-2">
                        <a href='/'>
                            <img src={logo} width={134} height={34} />
                        </a>
                    </div>
                </div>
            </div>
        </header>
    );
};

export default SimpleHeader