import streamlit as st
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity


# -----------------------------
# FAQ DATA
# -----------------------------

questions = [
    "What is CodeAlpha?",
    "What is an internship?",
    "How can I submit my project?",
    "What is GitHub?",
    "What is artificial intelligence?",
    "What is AI?",
    "What is machine learning?",
    "What is NLP?",
    "What is natural language processing?",
    "How can I contact support?"
]

answers = [
    "CodeAlpha is an organization that provides internship and learning opportunities.",
    "An internship gives students practical experience by working on real projects.",
    "You can submit your project by uploading the source code to GitHub and providing the repository link.",
    "GitHub is a platform used to store, manage and share source code.",
    "Artificial Intelligence is the field of creating systems that can perform tasks requiring human-like intelligence.",
    "AI stands for Artificial Intelligence. It enables computers to perform tasks that normally require human intelligence.",
    "Machine Learning allows computers to learn patterns from data and make predictions or decisions.",
    "NLP stands for Natural Language Processing. It helps computers understand and process human language.",
    "Natural Language Processing helps computers understand, process and analyze human language.",
    "You can contact the appropriate support team through the official communication channel provided by your organization."
]


# -----------------------------
# CREATE NLP MODEL
# -----------------------------

vectorizer = TfidfVectorizer(
    lowercase=True,
    stop_words="english"
)

question_vectors = vectorizer.fit_transform(questions)


# -----------------------------
# FIND BEST ANSWER
# -----------------------------

def get_answer(user_question):

    user_vector = vectorizer.transform([user_question])

    similarities = cosine_similarity(
        user_vector,
        question_vectors
    )

    best_match = similarities.argmax()
    confidence = similarities[0][best_match]

    # Minimum confidence required
    if confidence < 0.60:
        return (
            "Sorry, I don't understand that question. "
            "Please try asking one of the FAQ questions below.",
            confidence,
            False
        )

    return answers[best_match], confidence, True


# -----------------------------
# STREAMLIT PAGE
# -----------------------------

st.set_page_config(
    page_title="AI FAQ Chatbot",
    page_icon="🤖",
    layout="centered"
)

st.title("🤖 AI FAQ Chatbot")

st.write(
    "Ask a question and the chatbot will "
    "find the most relevant answer using NLP."
)


# -----------------------------
# USER INPUT
# -----------------------------

user_question = st.text_input(
    "Enter your question:",
    placeholder="Example: What is machine learning?"
)


# -----------------------------
# CHATBOT RESPONSE
# -----------------------------

if user_question:

    answer, confidence, matched = get_answer(user_question)

    st.subheader("🤖 Chatbot")

    st.write(answer)

    if matched:
        st.caption(
            f"Match confidence: {confidence:.2%}"
        )
    else:
        st.caption(
            f"Match confidence: {confidence:.2%} "
            "(below required threshold)"
        )


# -----------------------------
# EXAMPLE QUESTIONS
# -----------------------------

st.divider()

st.subheader("💡 Example Questions")

for question in questions:
    st.write("• " + question)


# -----------------------------
# PROJECT INFORMATION
# -----------------------------

st.divider()

st.subheader("📌 About this Project")

st.write(
    "This FAQ chatbot uses Natural Language Processing "
    "(NLP), TF-IDF vectorization and cosine similarity "
    "to find the most relevant answer to a user's question."
)

st.write(
    "**Technology:** Python, Streamlit, Scikit-learn, NLP"
)