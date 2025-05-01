import "./SecundaryBtn.css";

function SecundaryBtn({ text, onClick, loading }) {
    return <button
        type="button"
        className="w-100 add-to-cart-btn"
        onClick={onClick}
    >
        {
            loading ?
                <div className="spinner-border text-primary spinner-border-sm" role="status">
                    <span className="visually-hidden">Loading...</span>
                </div>
                : 
                text
        }
    </button>;
}

export default SecundaryBtn;