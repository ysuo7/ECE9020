import streamlit as st
import datetime

def tab1_content():
    st.title("INFIX Calculator")

    # Session state to store the stack and input
    if "stack" not in st.session_state:
        st.session_state.stack = []
    if "input" not in st.session_state:
        st.session_state.input = ""
    if "current" not in st.session_state:
        st.session_state.current = ""



    # Function to handle button clicks
    def button_click(label):
        # log = f"time: {datetime.now().strftime('%Y-%m-%d %H:%M:%S.%f')[:-3]}, button click: {label}"
        # with open('./rpn.log', 'a') as f:
        #     print(log)
        #     f.write(log+'\n')
        if label in "0123456789":
            st.session_state.input += label
            st.session_state.current += label
        elif label in ['\u2212', '\uff0b', 'x', '/']:
            if label == "\uff0b": # label + is not working
                st.session_state.input += '+'
                st.session_state.stack.append(st.session_state.current)
                st.session_state.stack.append('+')
                st.session_state.current = ""
            elif label == "\u2212": # label - is not working
                st.session_state.input += '-'
                st.session_state.stack.append(st.session_state.current)
                st.session_state.stack.append('-')
                st.session_state.current = ""
            elif label == "x": # label * is not working
                st.session_state.input += '*'
                st.session_state.stack.append(st.session_state.current)
                st.session_state.stack.append('*')
                st.session_state.current = ""
            elif label == "/":
                st.session_state.input += '/ '
                st.session_state.stack.append(st.session_state.current)
                st.session_state.stack.append('/')
                st.session_state.current = ""

        elif label == '=':
            st.session_state.stack.append(st.session_state.current)
            st.session_state.current = ""
            calculate_result()
        elif label == "C":
            st.session_state.stack = []
            st.session_state.input = ""
            st.session_state.current = ""

    def calculate_result():
        print(st.session_state.stack)
        while len(st.session_state.stack) > 1:
            num1 = st.session_state.stack.pop(0)
            operator = st.session_state.stack.pop(0)
            num2 = st.session_state.stack.pop(0)
            print(f"{num1} {operator} {num2}")
            result = eval(f'{num1}{operator}{num2}')
            st.session_state.stack.insert(0, result)

        if st.session_state.stack:
            st.session_state.input = str(st.session_state.stack[0])
            st.session_state.stack = []


    # Display the stack and current input
    st.text_input("Input", value=st.session_state.input, key="infix_display", disabled=True)
    st.text_input("Cur", value=st.session_state.current, key="infix_curr_display", disabled=True)
    st.text_input("Stack", value=st.session_state.stack, key="infix_stack_display", disabled=True)

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