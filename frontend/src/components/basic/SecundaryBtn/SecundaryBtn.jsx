import "./SecundaryBtn.css";

function SecundaryBtn({ text, onClick, loading }) {
    return <button
        type="button"
        className="w-100 add-to-cart-btn"
        onClick={onClick}
    >
        {
            loading ?
                <div class="spinner-border text-primary" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
                :
                text
        }
    </button>;
}

export default SecundaryBtn;