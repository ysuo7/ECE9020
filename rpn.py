import streamlit as st

def tab2_content():
    st.title("RPN Calculator")

    # Session state to store the stack and input
    if "stack" not in st.session_state:
        st.session_state.stack = []
    if "input" not in st.session_state:
        st.session_state.input = ""

    # Function to handle button clicks
    def button_click(label):
        if label in "0123456789":
            st.session_state.stack.append(label)
        elif label in ['\u2212', '\uff0b', 'x', '/']:
            b = int(st.session_state.stack.pop())
            a = int(st.session_state.stack.pop())
            if label == "\uff0b": # label + is not working
                print(a, b)
                st.session_state.stack.append(a + b)
            elif label == "\u2212": # label - is not working
                st.session_state.stack.append(a - b)
            elif label == "x": # label * is not working
                st.session_state.stack.append(a * b)
            elif label == "/":
                st.session_state.stack.append(a / b) 
        elif label == "C":
            st.session_state.stack = []
            st.session_state.input = ""
        elif label == "=":
            st.session_state.input = st.session_state.stack.pop()
    
    # Display the stack and current input
    st.text_input("Input", value=st.session_state.input, key="rpn_display", disabled=True)

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
            cols[i].button(label, on_click=button_click, args=(label,), key=f"rpn_{label}", use_container_width=True)