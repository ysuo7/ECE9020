import streamlit as st

def tab1_content():
    st.title("INFIX Calculator")

    # Session state to store the stack and input
    if "stack" not in st.session_state:
        st.session_state.stack = []
    if "input" not in st.session_state:
        st.session_state.input = ""

    # Function to handle button clicks
    def button_click(label):
        if label in "0123456789":
            if len(st.session_state.input) > 0:
                st.session_state.input = str(int(eval(st.session_state.input + label)))
            else:
                st.session_state.input += label
        elif label in ['\u2212', '\uff0b', 'x', '/']:
            if label == "\uff0b": # label + is not working
                st.session_state.input += '+'
            elif label == "\u2212": # label - is not working
                st.session_state.input += '-'
            elif label == "x": # label * is not working
                st.session_state.input += '*'
            elif label == "/":
                st.session_state.input += '/ '
        elif label == "C":
            st.session_state.stack = []
            st.session_state.input = ""


    # Display the stack and current input
    st.text_input("Input", value=st.session_state.input, key="infix_display", disabled=True)

    # Layout for the calculator buttons
    buttons = [
        ["7", "8", "9", "/"],
        ["4", "5", "6", "x"],
        ["1", "2", "3", "\u2212"],
        ["0", "C", "=", "\uff0b"]
    ]

    for row in buttons:
        cols = st.columns(4)
        for i, label in enumerate(row):
            cols[i].button(label, on_click=button_click, args=(label,), key=f"infix_{label}", use_container_width=True)