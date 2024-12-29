import { createRoot } from "react-dom/client";
import { App } from "./App";

const ROOT_ELEMENT_ID = "app"; // Extracted constant
const rootElement = document.getElementById(ROOT_ELEMENT_ID); // Renamed for clarity

createRoot(rootElement).render(<App />); // Updated to modern React API