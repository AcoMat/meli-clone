function LoadingSwitch({ children, loading, error }) {
    return (
        <>
            {loading ?
                <div className="spinner-border text-secondary mx-auto d-block" style={{marginTop: "200px"}} role="status">
                    <span className="visually-hidden">Loading...</span>
                </div>
                :
                children
            }
        </>
    );
}


export default LoadingSwitch;