function LargeBlueButton({ text, onClick, loading }) {
    return <button type="button" disabled={loading} className="blue-btn w-100" onClick={onClick}>
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

export default LargeBlueButton;