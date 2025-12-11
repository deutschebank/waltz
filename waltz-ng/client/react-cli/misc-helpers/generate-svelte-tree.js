const fs = require('fs');
const path = require('path');

// --- Configuration ---
const SVELTE_ROOT = path.resolve(__dirname, '../');
const OUTPUT_FILE = path.resolve(__dirname, './svelte_dependency_tree.md');
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

function generateDependencyTree(componentPath, dependencyMap, visited, prefix = '') {
    if (visited.has(componentPath)) {
        return `${prefix}└── ${path.basename(componentPath)} [Circular Reference]\n`;
    }
    visited.add(componentPath);

    let treeString = '';
    const dependencies = dependencyMap.get(componentPath) || [];

    dependencies.forEach((dep, index) => {
        const isLast = index === dependencies.length - 1;
        const connector = isLast ? '└── ' : '├── ';
        treeString += `${prefix}${connector}${path.basename(dep)}\n`;
        treeString += generateDependencyTree(dep, dependencyMap, new Set(visited), `${prefix}${isLast ? '    ' : '│   '}`);
    });

    return treeString;
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

    console.log('Generating dependency tree...');
    let output = '# Svelte Component Dependency Tree\n\n';
    const sortedFiles = [...allSvelteFiles].sort((a, b) => path.basename(a).localeCompare(path.basename(b)));

    sortedFiles.forEach(componentPath => {
        const relativePath = path.relative(SVELTE_ROOT, componentPath);
        output += `**${relativePath}**\n\n`;
        const tree = generateDependencyTree(componentPath, dependencyMap, new Set());
        output += tree
            ? '```\n' + tree + '```\n\n'
            : '_No dependencies_\n\n';
    });

    try {
        fs.writeFileSync(OUTPUT_FILE, output);
        console.log(`Successfully wrote dependency tree to ${OUTPUT_FILE}`);
    } catch (error) {
        console.error(`Failed to write to output file ${OUTPUT_FILE}:`, error);
    }
}

main();
