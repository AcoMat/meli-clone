import './HomeCard.css'
export default function HomeCard({ data, navigate }) {

    return (
        <article
        className={`bg-body d-flex flex-column h-100 w-100 p-3 rounded text-decoration-none text-dark home-card`} 
        onClick={() => navigate(data.link)}
        style={{cursor: "pointer"}}
        >
            <span className="fw-medium">{data.header}</span>
            <img className='img-fluid w-75 my-2 mx-auto' src={data.img} />
            <div className="d-flex flex-column justify-content-between h-100 text-center">
                <span className='card-desc' style={{ fontSize: "14px" }}>{data.desc}</span>
                <span className='fw-medium border bg-primary-subtle text-primary rounded' style={{ fontSize: "12px" }}>{data.footer}</span>
            </div>
        </article>
    )
}