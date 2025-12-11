const fs = require("node:fs")
const path = require("node:path");

// --- Configuration ---
const SVELTE_ROOT = path.resolve(__dirname, '../');
const OUTPUT_FILE = path.resolve(__dirname, './svelte_dependency_tree.csv');
// --- End Configuration ---

function findFilesRecursive(startPath, filter) {
    let results = [];
    try {
        if (!fs.existsSync(startPath)) {
            console.log("Directory not found: ", startPath);
            return [];
        }

        const files = fs.readdirSync(startPath);
        for (let i = 0; i < files.length; i++) {
            const filename = path.join(startPath, files[i]);
            const stat = fs.lstatSync(filename);
            if (stat.isDirectory()) {
                results = results.concat(findFilesRecursive(filename, filter));
            } else if (filename.endsWith(filter)) {
                results.push(filename);
            }
        }
    } catch (error) {
        console.error(`Error reading directory ${startPath}:`, error);
    }
    return results;
}

function getSvelteDependencies(filePath) {
    try {
        const content = fs.readFileSync(filePath, 'utf8');
        const dependencies = new Set();
        const importRegex = /import\s+[\w\d{},*\s]+\s+from\s+['"](.+\.svelte)['"]/g;
        let match;
        while ((match = importRegex.exec(content)) !== null) {
            dependencies.add(path.resolve(path.dirname(filePath), match[1]));
        }
        return Array.from(dependencies);
    } catch (error) {
        console.error(`Error reading or parsing file ${filePath}:`, error);
        return [];
    }
}

function main() {
    console.log('Starting Svelte dependency analysis...');
    const allSvelteFiles = findFilesRecursive(SVELTE_ROOT, '.svelte');
    if (allSvelteFiles.length === 0) {
        console.log('No .svelte files found.');
        return;
    }
    console.log(`Found ${allSvelteFiles.length} Svelte files.`);

    const dependencyMap = new Map();
    allSvelteFiles.forEach(file => {
        dependencyMap.set(file, getSvelteDependencies(file));
    });

    console.log('Generating CSV output...');
    let csvOutput = 'Component,Dependency\n'; // CSV Header

    dependencyMap.forEach((dependencies, componentPath) => {
        const componentRelativePath = path.relative(SVELTE_ROOT, componentPath);
        if (dependencies.length === 0) {
            csvOutput += `"${componentRelativePath}",\n`;
        } else {
            dependencies.forEach(depPath => {
                const depRelativePath = path.relative(SVELTE_ROOT, depPath);
                csvOutput += `"${componentRelativePath}","${depRelativePath}"\n`;
            });
        }
    });

    try {
        fs.writeFileSync(OUTPUT_FILE, csvOutput);
        console.log(`Successfully wrote dependency data to ${OUTPUT_FILE}`);
    } catch (error) {
        console.error(`Failed to write to output file ${OUTPUT_FILE}:`, error);
    }
}

main();
