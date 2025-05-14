import "./BlueButton.css";

function BlueButton({ text, onClick }) {
    return <button type="button" className="blue-btn" onClick={onClick}>{text}</button>;
}

export default BlueButton;