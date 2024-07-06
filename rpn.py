import streamlit as st
from datetime import datetime

def tab2_content():
    st.title("RPN Calculator")

    # Session state to store the stack and input
    if "stack" not in st.session_state:
        st.session_state.stack = []
    if "input" not in st.session_state:
        st.session_state.input = ""
    if "display" not in st.session_state:
        st.session_state.display = False

    # Function to handle button clicks
    def button_click(label):
        # log = f"time: {datetime.now().strftime('%Y-%m-%d %H:%M:%S.%f')[:-3]}, button click: {label}"
        # with open('./rpn.log', 'a') as f:
        #     print(log)
        #     f.write(log+'\n')
        if label in "0123456789":
            if st.session_state.display:
                st.session_state.display = False
                st.session_state.input = label
            else:
                st.session_state.input += label
        elif label in ['\u2212', '\uff0b', 'x', '/']:
            # push
            st.session_state.stack.append(st.session_state.input)

            b = int(st.session_state.stack.pop())
            a = int(st.session_state.stack.pop())
            result = 0
            if label == "\uff0b": # label + is not working
                print(a, b)
                result = a + b
            elif label == "\u2212": # label - is not working
                result = a - b
            elif label == "x": # label * is not working
                result = a * b
            elif label == "/":
                result = a / b
            st.session_state.stack.append(str(result))
            st.session_state.input = result
            st.session_state.display = True

        elif label == "C":
            st.session_state.stack = []
            st.session_state.input = ""
        elif label == "Enter":
            st.session_state.stack.append(st.session_state.input)
            st.session_state.input = ""
    # Display the stack and current input
    st.text_input("Input", value=st.session_state.input, key="rpn_display", disabled=True)
    st.text_input("Stack", value=st.session_state.stack, key="rpn_stack_display", disabled=True)

    # Layout for the calculator buttons
    buttons = [
        ["7", "8", "9", "/"],
        ["4", "5", "6", "x"],
        ["1", "2", "3", "\u2212"],
        ["0", "C", "Enter", "\uff0b"]
    ]

    for row in buttons:
        cols = st.columns(4)
        for i, label in enumerate(row):
            cols[i].button(label, on_click=button_click, args=(label,), key=f"rpn_{label}", use_container_width=True)