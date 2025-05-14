import './AmountSelector.css';

function AmountSelector({amount, setAmount}) {

    return (
        <div className="btn-group">
            <button type="button" className="btn dropdown-toggle border-0" data-bs-toggle="dropdown" aria-expanded="false">
                Cantidad: <strong>{amount} {amount > 1 ? 'unidades' : 'unidad'}</strong>
            </button>
            <ul className="dropdown-menu dropdown-menu-end">
                <li><button className={`dropdown-item ${amount == 1 ? 'selected' : ''} `} onClick={() => setAmount(1)} type="button">1 unidad</button></li>
                <li><button className={`dropdown-item ${amount == 2 ? 'selected' : ''} `} onClick={() => setAmount(2)} type="button">2 unidades</button></li>
                <li><button className={`dropdown-item ${amount == 3 ? 'selected' : ''} `} onClick={() => setAmount(3)} type="button">3 unidades</button></li>
                <li><button className={`dropdown-item ${amount == 4 ? 'selected' : ''} `} onClick={() => setAmount(4)} type="button">4 unidades</button></li>
                <li><button className={`dropdown-item ${amount == 5 ? 'selected' : ''} `} onClick={() => setAmount(5)} type="button">5 unidades</button></li>
                <li><button className={`dropdown-item ${amount == 6 ? 'selected' : ''} `} onClick={() => setAmount(6)} type="button">6 unidades</button></li>
            </ul>
        </div>
    );
}

export default AmountSelector;