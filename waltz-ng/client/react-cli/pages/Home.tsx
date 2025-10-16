// src/pages/Home.tsx
import React from "react";
import Button from "../components/Button/Button";

const Home: React.FC = () => {
    return (
        <div>
            <h1>Home Page</h1>
            <p>
                This is a simple React app using TypeScript and best practices.
            </p>
            <Button label="Submit" onClick={() => console.log("IN")} />
        </div>
    );
};

export default Home;
