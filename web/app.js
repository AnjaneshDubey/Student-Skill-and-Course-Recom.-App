// Navigation Logic
function showSection(id) {
    document.querySelectorAll('.section').forEach(sec => sec.classList.remove('active'));
    document.getElementById(id).classList.add('active');
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

// Auto-fill from Home
function autoFillDiscover(category) {
    document.getElementById('categorySelect').value = category;
    showSection('discover');
}

// Smart Search (Fuzzy Match) for Category
const categories = [
    "Frontend Development", "Backend Development", 
    "Data Science", "Data Analytics", "UI/UX Design", "Cloud Computing", 
    "Machine Learning", "Artificial Intelligence", "Cybersecurity"
];
const categoryInput = document.getElementById('categorySelect');
const suggestionsBox = document.getElementById('categorySuggestions');

categoryInput.addEventListener('input', () => {
    const val = categoryInput.value.toLowerCase();
    suggestionsBox.innerHTML = '';
    if (!val) {
        suggestionsBox.classList.remove('active');
        return;
    }
    
    // Fuzzy matching logic
    const matches = categories.filter(cat => cat.toLowerCase().includes(val) || val.split(' ').every(v => cat.toLowerCase().includes(v)));
    
    if (matches.length > 0) {
        suggestionsBox.classList.add('active');
        matches.forEach(match => {
            const div = document.createElement('div');
            div.className = 'suggestion-item';
            div.innerText = match;
            div.onclick = () => {
                categoryInput.value = match;
                suggestionsBox.classList.remove('active');
            };
            suggestionsBox.appendChild(div);
        });
    } else {
        suggestionsBox.classList.remove('active');
    }
});

// Close suggestions on outside click
document.addEventListener('click', (e) => {
    if (e.target !== categoryInput && e.target !== suggestionsBox) {
        suggestionsBox.classList.remove('active');
    }
});

// Custom Dropdown Logic
const dropdown = document.getElementById('difficultyDropdown');
const selectedValue = dropdown.querySelector('.selected-value');
const options = dropdown.querySelectorAll('.option');
const hiddenInput = document.getElementById('difficultySelect');

selectedValue.addEventListener('click', (e) => {
    e.stopPropagation();
    dropdown.classList.toggle('active');
});

options.forEach(option => {
    option.addEventListener('click', () => {
        selectedValue.innerText = option.innerText;
        hiddenInput.value = option.getAttribute('data-value');
        dropdown.classList.remove('active');
    });
});

document.addEventListener('click', (e) => {
    if (!dropdown.contains(e.target)) {
        dropdown.classList.remove('active');
    }
});

// Quiz System
const quizData = [
    { q: "What is HTML?", options: ["Programming Language", "Markup Language", "Database", "Framework"], ans: 1 },
    { q: "Which tool is used for version control?", options: ["Docker", "Git", "NPM", "Jenkins"], ans: 1 },
    { q: "What does CSS stand for?", options: ["Cascading Style Sheets", "Computer Style Sheets", "Creative Style System", "Color System"], ans: 0 },
    { q: "Which is a JavaScript framework?", options: ["Django", "Laravel", "React", "Spring"], ans: 2 }
];

function renderQuiz() {
    const container = document.getElementById('quizContainer');
    let html = '';
    quizData.forEach((item, index) => {
        html += `<div class="quiz-question">
            <h4>${index + 1}. ${item.q}</h4>
            <div class="quiz-options">
                ${item.options.map((opt, i) => `
                    <label><input type="radio" name="q${index}" value="${i}"> ${opt}</label>
                `).join('')}
            </div>
        </div>`;
    });
    container.innerHTML = html;
}
renderQuiz();

// State
let studentProfile = {};
let skillScore = 0;
let proficiency = 'Beginner';
let skillChartInstance = null;
let enrolledCourses = [];

function saveProfile() {
    studentProfile = {
        name: document.getElementById('studentName').value,
        education: document.getElementById('education').value,
        branch: document.getElementById('branch').value,
        targetRole: document.getElementById('targetRole').value,
        bio: document.getElementById('bio').value,
        githubLink: document.getElementById('githubLink').value,
        portfolioLink: document.getElementById('portfolioLink').value,
        workEnv: document.getElementById('workEnv').value
    };
    alert('Profile saved successfully! Proceed to Skill Evaluation.');
    showSection('evaluation');
}

function submitQuiz() {
    if (!studentProfile.name) {
        alert('Please save your profile first.');
        showSection('profile');
        return;
    }
    
    let score = 0;
    quizData.forEach((item, index) => {
        const selected = document.querySelector(`input[name="q${index}"]:checked`);
        if (selected && parseInt(selected.value) === item.ans) {
            score += 25;
        }
    });
    
    skillScore = score;
    if (score === 100) proficiency = 'Advanced';
    else if (score >= 50) proficiency = 'Intermediate';
    else proficiency = 'Beginner';
    
    updateDashboard();
    showSection('dashboard');
}

function updateDashboard() {
    document.getElementById('skillScore').innerText = skillScore;
    document.getElementById('proficiencyLevel').innerText = proficiency;
    
    let role = studentProfile.targetRole || "Full Stack Developer";
    document.getElementById('careerRecommendation').innerText = `Based on your profile, you are suited for: ${role}`;
    
    // Skill gap logic
    let gaps = "";
    if (proficiency === 'Beginner') gaps = "Missing foundational concepts and advanced frameworks.";
    else if (proficiency === 'Intermediate') gaps = "Missing advanced state management, system design, and optimization techniques.";
    else gaps = "Focus on leadership, architecture, and niche edge-cases.";
    
    document.getElementById('gapAnalysis').innerHTML = `<p class="muted-text">${gaps}</p><button onclick="autoFillDiscover('${role}')" class="primary-btn mt-4">Find Courses to fill gap</button>`;
    
    generateRoadmap(role, proficiency);
    renderChart(skillScore);
}

function generateRoadmap(role, prof) {
    let roadmapSteps = [];
    role = role.toLowerCase();
    
    if (role.includes('data science') || role.includes('data scientist')) {
        roadmapSteps = [
            { title: "Foundations", desc: "Python Basics, Variables, Loops" },
            { title: "Core Libraries", desc: "Pandas, NumPy, Data Cleaning" },
            { title: "Data Visualization", desc: "Matplotlib, Seaborn, EDA" },
            { title: "Machine Learning", desc: "Regression, Classification, Scikit-Learn" },
            { title: "Advanced", desc: "Deep Learning, Neural Networks, Deployment" }
        ];
    } else if (role.includes('data analytics') || role.includes('analyst')) {
        roadmapSteps = [
            { title: "Foundations", desc: "Excel Advanced, Pivot Tables" },
            { title: "Data Querying", desc: "SQL Basics, Joins, Aggregation" },
            { title: "Data Prep", desc: "Data Cleaning, ETL Basics" },
            { title: "BI Tools", desc: "Tableau, PowerBI, Dashboards" },
            { title: "Advanced", desc: "Predictive Modeling, Business Logic (DAX)" }
        ];
    } else if (role.includes('ui') || role.includes('design')) {
        roadmapSteps = [
            { title: "Foundations", desc: "Color Theory, Typography, Layout" },
            { title: "Wireframing", desc: "Low-Fidelity Sketches, Penpot" },
            { title: "Prototyping", desc: "Figma Auto-layout, Interactive Elements" },
            { title: "UX Research", desc: "User Personas, A/B Testing" },
            { title: "Advanced", desc: "Design Systems, Hand-off to Developers" }
        ];
    } else if (role.includes('cyber') || role.includes('security')) {
        roadmapSteps = [
            { title: "Foundations", desc: "Networking Basics, OS Fundamentals (Linux)" },
            { title: "Core Security", desc: "Threat Modeling, Cryptography Basics" },
            { title: "Vulnerability Scanning", desc: "Nmap, Wireshark, Burp Suite" },
            { title: "Ethical Hacking", desc: "Pen Testing, Metasploit, Web Exploitation" },
            { title: "Advanced", desc: "Incident Response, Malware Analysis" }
        ];
    } else if (role.includes('cloud')) {
        roadmapSteps = [
            { title: "Foundations", desc: "Virtualization, Networking Basics" },
            { title: "Cloud Platforms", desc: "AWS Core Services (EC2, S3, IAM)" },
            { title: "Containerization", desc: "Docker, Images, Containers" },
            { title: "Orchestration", desc: "Kubernetes Pods, Services, Deployments" },
            { title: "Advanced", desc: "Infrastructure as Code (Terraform), CI/CD" }
        ];
    } else if (role.includes('ai') || role.includes('machine learning') || role.includes('ml')) {
        roadmapSteps = [
            { title: "Foundations", desc: "Linear Algebra, Calculus, Python Stats" },
            { title: "Classic ML", desc: "Supervised & Unsupervised Learning" },
            { title: "Deep Learning", desc: "Neural Networks, PyTorch/TensorFlow" },
            { title: "Specialization", desc: "NLP, Computer Vision, Transformers" },
            { title: "Advanced", desc: "Generative AI, MLOps, Model Serving" }
        ];
    } else {
        // Default Fullstack/Web
        roadmapSteps = [
            { title: "Foundations", desc: "HTML Semantic Tags, CSS Flexbox & Grid" },
            { title: "Interactivity", desc: "JavaScript ES6, DOM Manipulation" },
            { title: "Frontend Frameworks", desc: "React/Vue, State Management, Routing" },
            { title: "Backend Systems", desc: "Node.js, Express, REST APIs" },
            { title: "Advanced", desc: "Databases (MongoDB/SQL), Deployment, Architecture" }
        ];
    }
        
    let roadmapHtml = '';
    roadmapSteps.forEach((step, i) => {
        roadmapHtml += `<div class="roadmap-step">
            <div class="step-number">${i+1}</div>
            <div class="roadmap-step-content">
                <h4>${step.title}</h4>
                <p>${step.desc}</p>
            </div>
        </div>`;
    });
    document.getElementById('learningRoadmap').innerHTML = roadmapHtml;
}

function renderChart(score) {
    const ctx = document.getElementById('skillChart').getContext('2d');
    if (skillChartInstance) skillChartInstance.destroy();
    
    let dataValues = score === 100 ? [90, 85, 95, 100] : score >= 50 ? [40, 60, 50, 75] : [10, 20, 15, 25];

    skillChartInstance = new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['Week 1', 'Week 2', 'Week 3', 'Current'],
            datasets: [{
                label: 'Skill Progression',
                data: dataValues,
                borderColor: '#a3bced',
                backgroundColor: 'rgba(163, 188, 237, 0.2)',
                borderWidth: 3,
                fill: true,
                tension: 0.4
            }]
        },
        options: {
            responsive: true,
            plugins: { legend: { display: false } },
            scales: { y: { beginAtZero: true, max: 100 } }
        }
    });
}

function toggleDrawer(card) {
    if (card.classList.contains('expanded')) {
        card.classList.remove('expanded');
    } else {
        // Close others
        document.querySelectorAll('.course-card.expanded').forEach(c => c.classList.remove('expanded'));
        card.classList.add('expanded');
    }
}

// Enrollment Logic
function enrollCourse(courseId, courseName, category, duration) {
    // Prevent duplicate enrollment
    if (enrolledCourses.find(c => c.id === courseId)) {
        alert("You are already enrolled in this course!");
        return;
    }
    
    enrolledCourses.push({
        id: courseId,
        name: courseName,
        category: category,
        duration: duration,
        progress: 0
    });
    
    // Update button visually
    const btn = document.getElementById(`enroll-btn-${courseId}`);
    if (btn) {
        btn.innerText = "Enrolled ✅";
        btn.style.background = "linear-gradient(135deg, var(--mint-green), #a8e6cf)";
    }
    
    renderEnrolledCourses();
    alert(`Successfully enrolled in ${courseName}! Check your Dashboard.`);
}

function renderEnrolledCourses() {
    const grid = document.getElementById('enrolledCoursesGrid');
    if (enrolledCourses.length === 0) {
        grid.innerHTML = '<p class="muted-text" style="grid-column: 1 / -1; margin-left: 10px;">You have not enrolled in any courses yet. Discover courses to begin!</p>';
        return;
    }
    
    let html = '';
    enrolledCourses.forEach(course => {
        html += `<div class="card course-card glass-card" style="border-top-color: var(--mint-green);">
            <h3>${course.name}</h3>
            <p class="muted-text" style="margin-top:0; font-size: 0.9em;"><b>Category:</b> ${course.category}</p>
            <div style="margin-top: 15px;">
                <p style="font-size: 0.85em; color: var(--text-secondary); margin-bottom: 5px;">Progress: ${course.progress}%</p>
                <div style="width: 100%; background: #e2e8f0; border-radius: 10px; height: 8px; overflow: hidden;">
                    <div style="width: ${course.progress}%; background: var(--mint-green); height: 100%;"></div>
                </div>
            </div>
            <button class="primary-btn" style="margin-top: 20px; padding: 10px 20px; font-size: 0.9em;" onclick="alert('Continuing course...')">Continue Learning</button>
        </div>`;
    });
    grid.innerHTML = html;
}

// Fetch Recommendations from Java API
async function fetchRecommendations() {
    let category = document.getElementById('categorySelect').value;
    const difficulty = hiddenInput.value;
    
    if (!category || !difficulty) {
        alert("Please select category and difficulty");
        return;
    }
    
    const dbCategories = [
        "Frontend Development", "Backend Development", 
        "Data Science", "Data Analytics", "UI/UX Design", "Cloud Computing", 
        "Cybersecurity", "Artificial Intelligence", "Machine Learning"
    ];
    
    let mappedCategory = category;
    
    for (let dbCat of dbCategories) {
        if(dbCat.toLowerCase().includes(category.toLowerCase()) || category.toLowerCase().includes(dbCat.toLowerCase())) {
            mappedCategory = dbCat;
            break;
        }
    }
    
    const grid = document.getElementById('coursesGrid');
    grid.innerHTML = '<p class="muted-text">Loading recommendations...</p>';
    
    try {
        const response = await fetch(`/api/recommend?category=${encodeURIComponent(mappedCategory)}&difficulty=${encodeURIComponent(difficulty)}`);
        const data = await response.json();
        
        if (data.length === 0) {
            grid.innerHTML = '<p class="muted-text">No exact courses found. Try adjusting criteria (e.g., changing difficulty).</p>';
            return;
        }
        
        let html = '';
        data.forEach(course => {
            const topics = course.advancedTopics ? course.advancedTopics.split('|') : [];
            const topicsHtml = topics.map(t => `<li>${t}</li>`).join('');
            
            html += `<div class="card course-card glass-card" onclick="toggleDrawer(this)">
                <div class="close-drawer" onclick="event.stopPropagation(); toggleDrawer(this.parentElement)">✕</div>
                <h3>${course.name}</h3>
                <p class="muted-text" style="margin-top:0"><b>Category:</b> ${course.category} | <b>Difficulty:</b> ${course.difficulty}</p>
                <p style="color:#f7a8b8; font-weight:bold; margin-top:10px">⭐ ${course.rating} Rating &nbsp;|&nbsp; ⏱️ ${course.duration} Hours</p>
                
                <div class="course-details-drawer" onclick="event.stopPropagation()">
                    <div style="background:var(--white); padding:15px; border-radius:12px; font-size:0.95em; margin-bottom: 20px;">
                        ${course.description}
                    </div>
                    
                    <h4 class="drawer-section-title">Tools We Recommend</h4>
                    <div>
                        ${course.freeTool ? `<span class="tool-tag free">🔧 Free: ${course.freeTool}</span>` : ''}
                        ${course.premiumTool ? `<span class="tool-tag premium">⭐ Premium: ${course.premiumTool}</span>` : ''}
                    </div>
                    
                    <h4 class="drawer-section-title">In-depth Topics Covered</h4>
                    <ul class="topics-list">
                        ${topicsHtml}
                    </ul>
                    
                    <div style="margin-top: 25px;">
                        <button class="primary-btn" id="enroll-btn-${course.id}" onclick="enrollCourse(${course.id}, '${course.name.replace(/'/g, "\\'")}', '${course.category}', ${course.duration})">
                            Start Roadmap
                        </button>
                    </div>
                </div>
            </div>`;
        });
        grid.innerHTML = html;
        
    } catch (error) {
        grid.innerHTML = '<p class="muted-text">Error loading recommendations.</p>';
        console.error(error);
    }
}
