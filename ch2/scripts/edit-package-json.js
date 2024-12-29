const fs = require('fs');

// Path to package.json
const packageJsonPath = './package.json';

// Read the existing package.json file
const packageJson = JSON.parse(fs.readFileSync(packageJsonPath, 'utf8'));

// Modify the package.json content
delete packageJson.main;
packageJson.name = "ch2";
packageJson.version = "1.0.0";
packageJson.description = "Creating a Web Application with Spring Boot";
packageJson.source = "src/main/javascript/index.js";
packageJson.targets = {
    default: {
        distDir: "target/classes/static"
    }
};
packageJson.keywords = [];
packageJson.author = "";
packageJson.license = "ISC";

// Write the updated package.json back to the file
fs.writeFileSync(packageJsonPath, JSON.stringify(packageJson, null, 2));
console.log('package.json updated successfully!');
