export default function HomeCard({ data, navigate }) {

    return (
        <article
        className={`bg-body d-flex flex-column rounded text-decoration-none w-50 text-dark`}
        onClick={() => navigate(data.link)}
        >
            <span className="fw-medium p-2 text-center">{data.header}</span>
            <img className='img-fluid w-50 my-2 mx-auto' src={data.img} />
            <div className="d-flex flex-column justify-content-between h-100 text-center">
                <span className='card-desc p-2' style={{ fontSize: "12px" }}>{data.desc}</span>
                <span className='fw-medium border bg-primary-subtle text-primary rounded m-2' style={{ fontSize: "12px" }}>{data.footer}</span>
            </div>
        </article>
    )
}